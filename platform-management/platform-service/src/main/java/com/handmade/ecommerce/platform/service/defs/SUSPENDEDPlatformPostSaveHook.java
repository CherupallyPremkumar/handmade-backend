package com.handmade.ecommerce.platform.service.defs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.event.PlatformSuspendedEvent;
import org.chenile.pubsub.ChenilePub;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class SUSPENDEDPlatformPostSaveHook implements PostSaveHook<PlatformOwner> {
    
    private static final Logger logger = LoggerFactory.getLogger(SUSPENDEDPlatformPostSaveHook.class);
    private static final String TOPIC = "platform.events";
    
    @Autowired
    private ChenilePub chenilePub;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void execute(PlatformOwner platform, TransientMap transientMap) {
        logger.warn("Platform {} has been SUSPENDED. Reason: {}", 
            platform.id, platform.suspensionReason);
        
        publishEvent(platform);
    }
    
    private void publishEvent(PlatformOwner platform) {
        try {
            PlatformSuspendedEvent event = new PlatformSuspendedEvent(platform);
            String eventJson = objectMapper.writeValueAsString(event);
            
            Map<String, Object> props = new HashMap<>();
            props.put("eventType", "PlatformSuspended");
            props.put("platformId", platform.id);
            
            chenilePub.asyncPublish(TOPIC, eventJson, props);
            logger.info("Published PlatformSuspendedEvent for platform {}", platform.id);
        } catch (Exception e) {
            logger.error("Failed to publish PlatformSuspendedEvent", e);
        }
    }
}
