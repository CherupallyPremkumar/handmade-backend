package com.handmade.ecommerce.platform.service.defs;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import org.chenile.stm.action.PostSaveHook;
import org.chenile.workflow.model.TransientMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MAINTENANCEPlatformPostSaveHook implements PostSaveHook<PlatformOwner> {
    
    private static final Logger logger = LoggerFactory.getLogger(MAINTENANCEPlatformPostSaveHook.class);
    
    @Override
    public void execute(PlatformOwner platform, TransientMap transientMap) {
        logger.info("Platform {} is in MAINTENANCE mode", platform.id);
    }
}
