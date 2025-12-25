package com.handmade.ecommerce.event.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.event.api.EventPublisher;
import org.chenile.pubsub.ChenilePub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class QueueBasedEventPublisher implements EventPublisher {
    
    private final Logger logger = LoggerFactory.getLogger(QueueBasedEventPublisher.class);
    
    public String topicNameTemplate;
    
    @Autowired 
    ChenilePub chenilePub;
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void publish(String topic, Object event) {
        logger.info("Publishing event to topic: {}", topic);
        Map<String, Object> props = new HashMap<>();
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            chenilePub.asyncPublish(topic, eventJson, props);
        } catch (Exception ignore) {
        }
    }
}
