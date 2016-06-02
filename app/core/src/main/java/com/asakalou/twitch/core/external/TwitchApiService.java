package com.asakalou.twitch.core.external;

import com.asakalou.twitch.core.task.RetryTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Set;


@Service
public class TwitchApiService {

    private static final Logger logger = LoggerFactory.getLogger(TwitchApiService.class);

    public static final String ROOT_URL = "https://api.twitch.tv/kraken/";
    public static final String STREAMS_URL = ROOT_URL + "streams?limit=100";
    public static final String GAMES_URL = ROOT_URL + "search/games?q=";

    @Autowired
    private RestTemplate twitchRestTemplate;

    public TwitchStreamsResponse getStreamsWithRetry(final Set<String> channels) throws Exception {
        return new RetryTask<TwitchStreamsResponse>() {
            @Override
            protected TwitchStreamsResponse execute() throws TwitchApiException {
                return getStreams(channels);
            }
        }.call();
    }

    public TwitchGameResponse getGamesWithRetry(final String name) throws Exception {
        return new RetryTask<TwitchGameResponse>() {
            @Override
            protected TwitchGameResponse execute() throws Exception {
                return getGames(name);
            }
        }.call();
    }

    public TwitchStreamsResponse getStreams(Set<String> channels) throws TwitchApiException {
        String url = STREAMS_URL;
        if (channels != null && !channels.isEmpty()) {
            url += "?channel=" + StringUtils.arrayToCommaDelimitedString(channels.toArray());
        }
        ResponseEntity<TwitchStreamsResponse> response = twitchRestTemplate.getForEntity(url, TwitchStreamsResponse.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            logAndThrowError(response);
        }

        return response.getBody();
    }

    public TwitchGameResponse getGames(String name) throws TwitchApiException {
        String url = GAMES_URL + name + "&type=suggest";

        ResponseEntity<TwitchGameResponse> response = twitchRestTemplate.getForEntity(url, TwitchGameResponse.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            logAndThrowError(response);
        }
        return response.getBody();
    }


    public void logAndThrowError(ResponseEntity responseEntity) throws TwitchApiException {
        String msg = "Twitch api access error. Status code " + responseEntity.getStatusCode();
        logger.warn(msg);
        throw new TwitchApiException(msg);
    }

}
