package com.asakalou.twitch.model;

import java.util.Date;


public class Channel {

    private String name;
    private String game;
    private String logo;
    private long currentViews;
    private ChannelStatus status;
    private Date lastModified;

    public Channel() {
    }

    public Channel(String name, String game, String logo, long currentViews, ChannelStatus status, Date lastModified) {
        this.name = name;
        this.game = game;
        this.logo = logo;
        this.currentViews = currentViews;
        this.status = status;
        this.lastModified = lastModified;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public long getCurrentViews() {
        return currentViews;
    }

    public void setCurrentViews(long currentViews) {
        this.currentViews = currentViews;
    }

    public ChannelStatus getStatus() {
        return status;
    }

    public void setStatus(ChannelStatus status) {
        this.status = status;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
