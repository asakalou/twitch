package com.asakalou.twitch.core.repository;

import com.asakalou.twitch.core.external.TwitchGame;
import com.asakalou.twitch.core.external.TwitchStream;
import com.asakalou.twitch.model.Channel;
import com.asakalou.twitch.model.ChannelEvent;
import com.asakalou.twitch.model.ChannelStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
public class CommonRepository {

    @Autowired
    private MongoTemplate template;

    public Set<String> getGames() {
        return template.findAll(com.asakalou.twitch.model.Game.class)
                .stream()
                .map(com.asakalou.twitch.model.Game::getName)
                .collect(Collectors.toSet());
    }

    public Set<String> getChannels() {
        return template.findAll(Channel.class)
                .stream()
                .map(Channel::getName)
                .collect(Collectors.toSet());
    }

    public void updateChannelStatus(String channelName, Date currentDate, ChannelStatus channelStatus, long currentViews) {
        template.updateFirst(new Query(Criteria.where("name").is(channelName)),
                new Update().set("status", channelStatus)
                        .set("lastModified", currentDate)
                        .set("currentViews", currentViews),
                Channel.class);
    }

    public void updateGame(TwitchGame game, TwitchStream stream) {
        template.upsert(Query.query(Criteria.where("name").is(stream.getGame())),
                Update.update("smallImgSrc", game.getLogo().get("small")),
                com.asakalou.twitch.model.Game.class);
    }

    public void insertEvents(List<ChannelEvent> channelEvents) {
        template.insert(channelEvents, ChannelEvent.class);
    }

    public void insertChannels(List<Channel> channels) {
        template.insert(channels, Channel.class);
    }


}
