package com.asakalou.twitch.core.external;

import java.util.List;


public class TwitchStreamsResponse {
    private long _total;
    private List<TwitchStream> streams;

    public TwitchStreamsResponse() {
    }

    public long get_total() {
        return _total;
    }

    public void set_total(long _total) {
        this._total = _total;
    }

    public List<TwitchStream> getStreams() {
        return streams;
    }

    public void setStreams(List<TwitchStream> streams) {
        this.streams = streams;
    }
}
