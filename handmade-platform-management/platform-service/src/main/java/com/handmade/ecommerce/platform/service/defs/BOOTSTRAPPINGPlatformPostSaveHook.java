package com.handmade.ecommerce.platform.service.defs;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BOOTSTRAPPINGPlatformPostSaveHook implements PostSaveHook<PlatformOwner> {
    
    private static final Logger logger = LoggerFactory.getLogger(BOOTSTRAPPINGPlatformPostSaveHook.class);
    
    @Override
    public void execute(PlatformOwner platform, TransientMap transientMap) {
        logger.info("Platform {} entered BOOTSTRAPPING state", platform.id);
    }
}
