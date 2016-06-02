package com.asakalou.twitch.rest;

import com.asakalou.twitch.rest.config.RestConfig;
import com.asakalou.twitch.rest.service.ChannelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Created by asakalou on 5/30/16.
 */

@ContextConfiguration(classes = {RestConfig.class})
public class ChannelsServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    ChannelsService channelsService;


    @Test
    public void testChannels() throws Exception {
//        channelsService.getChannelsWithMaxAvgViewers(10);

    }


}
