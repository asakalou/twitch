package com.asakalou.twitch.rest.controller;

import com.asakalou.twitch.model.Channel;
import com.asakalou.twitch.model.Game;
import com.asakalou.twitch.rest.service.CategoryType;
import com.asakalou.twitch.rest.service.ChannelHistory;
import com.asakalou.twitch.rest.service.ChannelsService;
import com.asakalou.twitch.rest.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    protected GamesService gamesService;

    @Autowired
    protected ChannelsService channelsService;

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    @ResponseBody
    public List<Game> games() {
        return gamesService.getGames();
    }

    @RequestMapping(value = "/channels", method = RequestMethod.GET)
    @ResponseBody
    public List<Channel> channels(@RequestParam(required = false, defaultValue = "10") int limit, CategoryType categoryType, long from) {
        return channelsService.getChannels(categoryType, limit, new Date(from));
    }

    @RequestMapping(value = "/channels/history", method = RequestMethod.GET)
    @ResponseBody
    public List<ChannelHistory> channelsHistory(String[] channels, long from) {
        return channelsService.historyData(Arrays.asList(channels), new Date(from));
    }

}
