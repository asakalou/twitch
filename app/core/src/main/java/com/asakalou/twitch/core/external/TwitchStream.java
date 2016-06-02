package com.asakalou.twitch.core.external;

import java.util.Date;


public class TwitchStream {
    private String game;
    private long viewers;
    private Date created_at;
    private TwitchChannel channel;
    private boolean is_playlist;

    public TwitchStream() {
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public long getViewers() {
        return viewers;
    }

    public void setViewers(long viewers) {
        this.viewers = viewers;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public TwitchChannel getChannel() {
        return channel;
    }

    public void setChannel(TwitchChannel channel) {
        this.channel = channel;
    }

    public boolean is_playlist() {
        return is_playlist;
    }

    public void setIs_playlist(boolean is_playlist) {
        this.is_playlist = is_playlist;
    }
}
