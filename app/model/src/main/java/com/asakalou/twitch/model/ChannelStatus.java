package com.asakalou.twitch.model;


public enum ChannelStatus {
    LIVE,
    REPLAY,
    OFFLINE;

    public static ChannelStatus fromPlaylist(Boolean isPlaylist) {
        return isPlaylist == null ? ChannelStatus.OFFLINE : isPlaylist
                ? ChannelStatus.REPLAY : ChannelStatus.LIVE;
    }
}
