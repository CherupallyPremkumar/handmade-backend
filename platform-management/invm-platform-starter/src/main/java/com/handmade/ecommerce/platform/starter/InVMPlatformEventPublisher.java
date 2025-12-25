package com.handmade.ecommerce.platform.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.platform.api.PlatformEventPublisher;
import org.chenile.core.event.EventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class InVMPlatformEventPublisher implements PlatformEventPublisher {
    
    private final Logger logger = LoggerFactory.getLogger(InVMPlatformEventPublisher.class);
    
    @Autowired 
    EventProcessor eventProcessor;
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void publishEvent(String topic, Object event) {
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
