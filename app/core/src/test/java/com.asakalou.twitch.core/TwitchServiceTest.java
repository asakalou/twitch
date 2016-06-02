package com.asakalou.twitch.core;

import com.asakalou.twitch.core.config.CoreConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(classes = {CoreConfig.class})
public class TwitchServiceTest extends AbstractTestNGSpringContextTests {

}
