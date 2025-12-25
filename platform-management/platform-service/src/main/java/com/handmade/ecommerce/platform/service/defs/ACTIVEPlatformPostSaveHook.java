package com.handmade.ecommerce.platform.service.defs;

import com.handmade.ecommerce.event.api.EventPublisher;
import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.event.PlatformActivatedEvent;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ACTIVEPlatformPostSaveHook implements PostSaveHook<PlatformOwner> {
    
    private static final Logger logger = LoggerFactory.getLogger(ACTIVEPlatformPostSaveHook.class);
    private static final String TOPIC = "platform.events";
    
    @Autowired
    private EventPublisher eventPublisher;
    
    @Override
    public void execute(PlatformOwner platform, TransientMap transientMap) {
        logger.info("Platform {} is now ACTIVE", platform.id);
        
        platform.suspended = false;
        platform.suspensionReason = null;
        
        publishEvent(platform);
    }
    
    private void publishEvent(PlatformOwner platform) {
        PlatformActivatedEvent event = new PlatformActivatedEvent(platform);
        eventPublisher.publish(TOPIC, event);
        logger.info("Published PlatformActivatedEvent for platform {}", platform.id);
    }
}
