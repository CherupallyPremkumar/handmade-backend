package com.handmade.ecommerce.platform.service.defs;

import com.handmade.ecommerce.platform.api.PlatformEventPublisher;
import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.event.PlatformSuspendedEvent;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SUSPENDEDPlatformPostSaveHook implements PostSaveHook<PlatformOwner> {
    
    private static final Logger logger = LoggerFactory.getLogger(SUSPENDEDPlatformPostSaveHook.class);
    private static final String TOPIC = "platform.events";
    
    @Autowired
    private PlatformEventPublisher platformEventPublisher;
    
    @Override
    public void execute(PlatformOwner platform, TransientMap transientMap) {
        logger.warn("Platform {} has been SUSPENDED. Reason: {}", 
            platform.id, platform.suspensionReason);
        
        publishEvent(platform);
    }
    
    private void publishEvent(PlatformOwner platform) {
        PlatformSuspendedEvent event = new PlatformSuspendedEvent(platform);
        platformEventPublisher.publishEvent(TOPIC, event);
        logger.info("Published PlatformSuspendedEvent for platform {}", platform.id);
    }
}
