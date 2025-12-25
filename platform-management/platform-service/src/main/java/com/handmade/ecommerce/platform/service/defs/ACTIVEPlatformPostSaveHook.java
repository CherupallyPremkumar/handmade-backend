package com.handmade.ecommerce.platform.service.defs;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import org.chenile.stm.action.PostSaveHook;
import org.chenile.workflow.model.TransientMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ACTIVEPlatformPostSaveHook implements PostSaveHook<PlatformOwner> {
    
    private static final Logger logger = LoggerFactory.getLogger(ACTIVEPlatformPostSaveHook.class);
    
    @Override
    public void execute(PlatformOwner platform, TransientMap transientMap) {
        logger.info("Platform {} is now ACTIVE", platform.id);
        
        platform.suspended = false;
        platform.suspensionReason = null;
    }
}
