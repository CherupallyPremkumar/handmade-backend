package com.handmade.ecommerce.platform.service.defs;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import org.chenile.stm.action.PostSaveHook;
import org.chenile.workflow.model.TransientMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SUSPENDEDPlatformPostSaveHook implements PostSaveHook<PlatformOwner> {
    
    private static final Logger logger = LoggerFactory.getLogger(SUSPENDEDPlatformPostSaveHook.class);
    
    @Override
    public void execute(PlatformOwner platform, TransientMap transientMap) {
        logger.warn("Platform {} has been SUSPENDED. Reason: {}", 
            platform.id, platform.suspensionReason);
    }
}
