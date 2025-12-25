package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.dto.ActivatePlatformPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Activate Platform Action
 * Transitions platform from BOOTSTRAPPING to ACTIVE state
 */
public class ActivatePlatformAction extends AbstractSTMTransitionAction<PlatformOwner, ActivatePlatformPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(ActivatePlatformAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                             ActivatePlatformPayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Activating platform: {}", platform.getId());
        
        // Set platform as active
        platform.getPlatformLifecycle().setActivatedAt(java.time.LocalDateTime.now());
        
        // Log activation
        logger.info("Platform {} activated successfully", platform.getId());
    }
}
