package com.asakalou.twitch.core.external;


public class TwitchApiException extends Exception {

    public TwitchApiException() {
    }

    public TwitchApiException(String message) {
        super(message);
    }

    public TwitchApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public TwitchApiException(Throwable cause) {
        super(cause);
    }

    public TwitchApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
