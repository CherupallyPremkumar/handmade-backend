package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.dto.SuspendPlatformPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Suspend Platform Action
 * Transitions platform from ACTIVE to SUSPENDED state
 */
public class SuspendPlatformAction extends AbstractSTMTransitionAction<PlatformOwner, SuspendPlatformPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(SuspendPlatformAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                             SuspendPlatformPayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.warn("Suspending platform: {} - Reason: {}", platform.id, payload.reason);
        
        // Update suspension status
        platform.suspended = true;
        platform.suspensionReason = payload.reason;
        
        logger.info("Platform {} suspended successfully", platform.id);
    }
}
