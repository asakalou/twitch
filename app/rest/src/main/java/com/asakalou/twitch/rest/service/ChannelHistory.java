package com.asakalou.twitch.rest.service;

import java.util.Date;
import java.util.List;

/**
 * Created by asakalou on 6/1/16.
 */
public class ChannelHistory {

    private String name;
    private List<Date> dates;
    private List<Long> viewers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public List<Long> getViewers() {
        return viewers;
    }

    public void setViewers(List<Long> viewers) {
        this.viewers = viewers;
    }
}
