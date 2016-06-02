package com.asakalou.twitch.core.task;

import com.asakalou.twitch.core.external.*;
import com.asakalou.twitch.core.repository.CommonRepository;
import com.asakalou.twitch.model.Channel;
import com.asakalou.twitch.model.ChannelEvent;
import com.asakalou.twitch.model.ChannelStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Service
public class TwitchDataPuller {

    private static final Logger logger = LoggerFactory.getLogger(TwitchDataPuller.class);

    @Autowired
    private TwitchApiService twitchApiService;

    @Autowired
    private CommonRepository commonRepository;

    // todo add exiting executor service (e.g. guava)
    private final ExecutorCompletionService<Void> executorService =
            new ExecutorCompletionService<>(Executors.newFixedThreadPool(10));


    // todo put this cons to properties file
    @Scheduled(fixedDelay = 2 * 60 * 1000)
    public void pullData() {
        logger.info("[Data Pull] Start");

        try {
            List<Future> tasks = new ArrayList<>();
            TwitchStreamsResponse streamsResponse = twitchApiService.getStreamsWithRetry(null);
            Date currentDate = new Date();

            Set<String> channels = commonRepository.getChannels();
            Set<String> games = commonRepository.getGames();

            List<Channel> channelsToAdd = new ArrayList<>();
            List<ChannelEvent> events = new ArrayList<>();

            for (TwitchStream stream : streamsResponse.getStreams()) {
                TwitchChannel channel = stream.getChannel();

                if (!channels.remove(channel.getName())) {
                    channelsToAdd.add(new Channel(channel.getName(), stream.getGame(),
                            channel.getLogo(), stream.getViewers(),
                            ChannelStatus.fromPlaylist(stream.is_playlist()), currentDate));
                } else {
                    commonRepository.updateChannelStatus(channel.getName(), currentDate,
                            ChannelStatus.fromPlaylist(stream.is_playlist()), stream.getViewers());
                }

                if (!games.contains(stream.getGame())) {
                    tasks.add(executorService.submit(() -> {
                        TwitchGameResponse gameResponse = twitchApiService.getGamesWithRetry(stream.getGame());
                        for (TwitchGame game : gameResponse.getGames()) {
                            commonRepository.updateGame(game, stream);
                        }
                        return null;
                    }));
                }

                events.add(new ChannelEvent(channel.getName(),
                        stream.getGame(),
                        stream.getViewers(),
                        currentDate,
                        ChannelStatus.fromPlaylist(stream.is_playlist())));
            }

            executorService.submit(() -> commonRepository.insertChannels(channelsToAdd), null);
            executorService.submit(() -> commonRepository.insertEvents(events), null);

            if (!channels.isEmpty()) {
                pullForChannels(channels);
            }

            for (Future submit : tasks) {
                try {
                    executorService.take();
                } catch (InterruptedException e) {
                    logger.error("Error taking tasks results", e);
                }
            }
        } catch (Exception e) {
            logger.error("Error during data pull", e);
        }

        logger.info("[Data Pull] End");
    }

    public void pullForChannels(Set<String> channels) throws Exception {
        TwitchStreamsResponse response = twitchApiService.getStreamsWithRetry(channels);

        List<ChannelEvent> events = new ArrayList<>();
        Date currentDate = new Date();
        for (TwitchStream stream : response.getStreams()) {
            events.add(new ChannelEvent(stream.getChannel().getName(),
                    stream.getGame(),
                    stream.getViewers(),
                    currentDate,
                    ChannelStatus.fromPlaylist(stream.is_playlist())));
            channels.remove(stream.getChannel().getName());
        }

        commonRepository.insertEvents(events);

        for (String channel : channels) {
            commonRepository.updateChannelStatus(channel, currentDate,
                    ChannelStatus.fromPlaylist(null), 0);
        }
    }

}
