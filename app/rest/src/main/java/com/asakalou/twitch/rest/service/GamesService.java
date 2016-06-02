package com.asakalou.twitch.rest.service;

import com.asakalou.twitch.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by asakalou on 5/31/16.
 */
@Service
public class GamesService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Game> getGames() {
        return mongoTemplate.findAll(Game.class);
    }

}
