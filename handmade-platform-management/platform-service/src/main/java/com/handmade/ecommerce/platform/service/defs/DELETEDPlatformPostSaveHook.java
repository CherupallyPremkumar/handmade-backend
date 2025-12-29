package com.handmade.ecommerce.platform.service.defs;

import com.handmade.ecommerce.event.api.EventPublisher;
import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.event.PlatformDeletedEvent;
import org.chenile.stm.State;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DELETEDPlatformPostSaveHook implements PostSaveHook<PlatformOwner> {
    
    private static final Logger logger = LoggerFactory.getLogger(DELETEDPlatformPostSaveHook.class);
    private static final String TOPIC = "platform.events";
    
    @Autowired
    private EventPublisher eventPublisher;
    
    @Override
    public void execute(PlatformOwner platform, TransientMap transientMap) {
        logger.info("Platform {} transitioned to DELETED state", platform.id);
        
        // Handle case where transientMap might be null
        if (transientMap != null) {
            String deletedBy = (String) transientMap.get("deletedBy");
            logger.info("Platform deleted by: {}", deletedBy);
            if (deletedBy != null) {
                platform.deletedBy = deletedBy;
            }
        }
        
        // Additional cleanup logic can be added here
        // For example: notify external systems, archive data, etc.
        
        publishEvent(platform);
    }
    
    private void publishEvent(PlatformOwner platform) {
        PlatformDeletedEvent event = new PlatformDeletedEvent(platform);
        eventPublisher.publish(TOPIC, event);
        logger.info("Published PlatformDeletedEvent for platform {}", platform.id);
    }
}
