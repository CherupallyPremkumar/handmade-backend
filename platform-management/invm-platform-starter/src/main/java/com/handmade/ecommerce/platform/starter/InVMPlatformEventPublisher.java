package com.handmade.ecommerce.platform.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.platform.domain.event.PlatformActivatedEvent;
import org.chenile.core.event.EventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class InVMPlatformEventPublisher {
    
    private final Logger logger = LoggerFactory.getLogger(InVMPlatformEventPublisher.class);
    
    @Autowired 
    EventProcessor eventProcessor;
    
    ObjectMapper objectMapper = new ObjectMapper();
    
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
