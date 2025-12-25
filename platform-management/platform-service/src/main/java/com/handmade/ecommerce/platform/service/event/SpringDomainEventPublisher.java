package com.handmade.ecommerce.platform.service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.platform.service.messaging.DomainEventPublisher;
import org.chenile.pubsub.ChenilePub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Domain Event Publisher using Chenile Pub/Sub
 * Publishes platform domain events to message queues asynchronously
 */
@Component
public class SpringDomainEventPublisher implements DomainEventPublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(SpringDomainEventPublisher.class);
    private static final String PLATFORM_EVENTS_TOPIC = "platform.events";
    
    @Autowired 
    private ChenilePub chenilePub;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void publish(Object event) {
        if (event == null) {
            logger.warn("Attempted to publish null event");
            return;
        }

        try {
            String eventJson = objectMapper.writeValueAsString(event);
            Map<String, Object> props = new HashMap<>();
            props.put("eventType", event.getClass().getSimpleName());
            props.put("timestamp", System.currentTimeMillis());
            
            logger.info("Publishing domain event to topic {}: {}", PLATFORM_EVENTS_TOPIC, event.getClass().getSimpleName());
            chenilePub.asyncPublish(PLATFORM_EVENTS_TOPIC, eventJson, props);
            
        } catch (Exception e) {
            logger.error("Failed to publish domain event: {}", event.getClass().getSimpleName(), e);
            // Don't throw - event publishing should not break the main flow
        }
    }

    @Override
    public void publishAll(Iterable<?> events) {
        if (events == null) {
            return;
        }
        
        events.forEach(this::publish);
    }
}
