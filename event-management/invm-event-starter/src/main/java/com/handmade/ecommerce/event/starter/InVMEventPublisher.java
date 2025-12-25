package com.handmade.ecommerce.event.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.event.api.EventPublisher;
import org.chenile.core.event.EventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class InVMEventPublisher implements EventPublisher {
    
    private final Logger logger = LoggerFactory.getLogger(InVMEventPublisher.class);
    
    @Autowired 
    EventProcessor eventProcessor;
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void publish(String topic, Object event) {
        logger.info("Publishing event to topic: {}", topic);
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            eventProcessor.handleEvent(topic, eventJson);
        } catch (Exception e) {
            logger.error("Failed to publish event", e);
            e.printStackTrace();
        }
    }
}
