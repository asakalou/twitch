package com.asakalou.twitch.core.external;

import java.util.Map;


public class TwitchGame {

    private String name;
    private Map<String, String> logo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getLogo() {
        return logo;
    }

    public void setLogo(Map<String, String> logo) {
        this.logo = logo;
    }
}
