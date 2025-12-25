package com.handmade.ecommerce.platform.service.defs;

import com.handmade.ecommerce.platform.api.PlatformEventPublisher;
import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.event.PlatformDeletedEvent;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DELETEDPlatformPostSaveHook implements PostSaveHook<PlatformOwner> {
    
    private static final Logger logger = LoggerFactory.getLogger(DELETEDPlatformPostSaveHook.class);
    private static final String TOPIC = "platform.events";
    
    @Autowired
    private PlatformEventPublisher platformEventPublisher;
    
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
        PlatformDeletedEvent event = new PlatformDeletedEvent(platform);
        platformEventPublisher.publishEvent(TOPIC, event);
        logger.info("Published PlatformDeletedEvent for platform {}", platform.id);
    }
}
