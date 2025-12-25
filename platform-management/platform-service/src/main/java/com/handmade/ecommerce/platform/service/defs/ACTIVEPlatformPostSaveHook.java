package com.handmade.ecommerce.platform.service.defs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.event.PlatformActivatedEvent;
import org.chenile.pubsub.ChenilePub;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class ACTIVEPlatformPostSaveHook implements PostSaveHook<PlatformOwner> {
    
    private static final Logger logger = LoggerFactory.getLogger(ACTIVEPlatformPostSaveHook.class);
    private static final String TOPIC = "platform.events";
    
    @Autowired
    private ChenilePub chenilePub;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void execute(PlatformOwner platform, TransientMap transientMap) {
        logger.info("Platform {} is now ACTIVE", platform.id);
        
        platform.suspended = false;
        platform.suspensionReason = null;
        
        publishEvent(platform);
    }
    
    private void publishEvent(PlatformOwner platform) {
        try {
            PlatformActivatedEvent event = new PlatformActivatedEvent(platform);
            String eventJson = objectMapper.writeValueAsString(event);
            
            Map<String, Object> props = new HashMap<>();
            props.put("eventType", "PlatformActivated");
            props.put("platformId", platform.id);
            
            chenilePub.asyncPublish(TOPIC, eventJson, props);
            logger.info("Published PlatformActivatedEvent for platform {}", platform.id);
        } catch (Exception e) {
            logger.error("Failed to publish PlatformActivatedEvent", e);
        }
    }
}
