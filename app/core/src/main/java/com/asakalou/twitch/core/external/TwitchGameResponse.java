package com.asakalou.twitch.core.external;

import java.util.List;


public class TwitchGameResponse {

    private List<TwitchGame> games;

    public List<TwitchGame> getGames() {
        return games;
    }

    public void setGames(List<TwitchGame> games) {
        this.games = games;
    }
}
