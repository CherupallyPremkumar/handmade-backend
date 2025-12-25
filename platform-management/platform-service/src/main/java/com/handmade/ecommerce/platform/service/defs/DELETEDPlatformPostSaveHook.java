package com.handmade.ecommerce.platform.service.defs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.event.PlatformDeletedEvent;
import org.chenile.pubsub.ChenilePub;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class DELETEDPlatformPostSaveHook implements PostSaveHook<PlatformOwner> {
    
    private static final Logger logger = LoggerFactory.getLogger(DELETEDPlatformPostSaveHook.class);
    private static final String TOPIC = "platform.events";
    
    @Autowired
    private ChenilePub chenilePub;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void execute(PlatformOwner platform, TransientMap transientMap) {
        logger.warn("Platform {} has been DELETED", platform.id);
        
        platform.deleted = true;
        platform.deletedAt = java.time.LocalDateTime.now();
        
        String deletedBy = (String) transientMap.get("deletedBy");
        if (deletedBy != null) {
            platform.deletedBy = deletedBy;
        }
        
        publishEvent(platform);
    }
    
    private void publishEvent(PlatformOwner platform) {
        try {
            PlatformDeletedEvent event = new PlatformDeletedEvent(platform);
            String eventJson = objectMapper.writeValueAsString(event);
            
            Map<String, Object> props = new HashMap<>();
            props.put("eventType", "PlatformDeleted");
            props.put("platformId", platform.id);
            
            chenilePub.asyncPublish(TOPIC, eventJson, props);
            logger.info("Published PlatformDeletedEvent for platform {}", platform.id);
        } catch (Exception e) {
            logger.error("Failed to publish PlatformDeletedEvent", e);
        }
    }
}
