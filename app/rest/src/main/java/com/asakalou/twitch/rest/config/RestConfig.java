package com.asakalou.twitch.rest.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;


@Configuration
@ComponentScan({"com.asakalou.twitch.rest.service"})
public class RestConfig {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        mongo.setHost("localhost");
        return new MongoTemplate(new MongoClient(), "twitch");
    }

}
