package com.asakalou.twitch.model;

import java.util.Date;


public class ChannelEvent {

    private String name;
    private String game;
    private long viewers;
    private Date eventDate;
    private ChannelStatus status;

    public ChannelEvent(String name, String game, long viewers, Date eventDate, ChannelStatus status) {
        this.name = name;
        this.game = game;
        this.viewers = viewers;
        this.eventDate = eventDate;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public ChannelStatus getStatus() {
        return status;
    }

    public void setStatus(ChannelStatus status) {
        this.status = status;
    }
}
