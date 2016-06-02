package com.asakalou.twitch.rest.service;

import com.asakalou.twitch.model.Channel;
import com.asakalou.twitch.model.ChannelEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by asakalou on 5/30/16.
 */
@Service
public class ChannelsService {

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Channel> getChannels(CategoryType categoryType, int limit, Date from) {
        switch (categoryType) {
            case AVG_VIEWERS: {
                return getChannelsAvgViewers(limit, from);
            }
            case CURRENT_VIEWERS: {
                return getChannelsCurrentViewers(limit, from);
            }
            case MAX_VIEWERS: {
                return getChannelsMaxViewers(limit, from);
            }
        }

        return Collections.emptyList();
    }

    public List<Channel> getChannelsCurrentViewers(int limit, Date from) {
        Query q = new Query()
                .addCriteria(Criteria.where("lastModified").gte(from))
                .with(new Sort(Sort.Direction.DESC, "currentViews"))
                .with(new PageRequest(0, limit));

        return mongoTemplate.find(q, Channel.class);
    }

    public List<Channel> getChannelsMaxViewers(int limit, Date from) {
        Query q = new Query()
                .addCriteria(Criteria.where("lastModified").gte(from))
                .with(new Sort(Sort.Direction.DESC, "maxViewers"))
                .with(new PageRequest(0, limit));

        return mongoTemplate.find(q, Channel.class);
    }

    public List<Channel> getChannelsAvgViewers(int limit, Date from) {
        Aggregation ag = Aggregation.newAggregation(
                Aggregation.group("name")
                        .first("$name").as("name")
                        .avg("$viewers").as("avgViewers"),
                Aggregation.sort(new Sort(Sort.Direction.DESC, "avgViewers")),
                Aggregation.limit(limit)
        );

        List<Channel> aggResult = mongoTemplate.aggregate(ag, ChannelEvent.class, Channel.class).getMappedResults();
        List<String> channelNames = aggResult
                .stream()
                .map(Channel::getName)
                .collect(Collectors.toList());

        List<Channel> channels = mongoTemplate.find(Query.query(Criteria.where("name").in(channelNames)), Channel.class);
        Map<String, Channel> channelMap = channels.stream().collect(Collectors.toMap(Channel::getName, Function.identity()));

        return aggResult.stream()
                .map(c -> channelMap.get(c.getName()))
                .collect(Collectors.toList());
    }


    public List<ChannelHistory> historyData(List<String> channels, Date from) {

        Aggregation ag = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("name").in(channels).andOperator(Criteria.where("eventDate").gt(from))),
                Aggregation.sort(new Sort(Sort.Direction.DESC, "eventDate")),
                Aggregation.group(Fields.fields("name")).push("$eventDate").as("dates").push("$viewers").as("viewers").first("$name").as("name")
        );

        List<ChannelHistory> history = mongoTemplate.aggregate(ag, ChannelEvent.class, ChannelHistory.class).getMappedResults();
        history.forEach(h -> {
            Collections.reverse(h.getDates());
            Collections.reverse(h.getViewers());
        });

        return history;
    }


}
