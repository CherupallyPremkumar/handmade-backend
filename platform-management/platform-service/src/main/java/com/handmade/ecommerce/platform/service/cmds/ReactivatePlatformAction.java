package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.dto.ReactivatePlatformPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reactivate Platform Action
 * Transitions platform from SUSPENDED back to ACTIVE state
 */
public class ReactivatePlatformAction extends AbstractSTMTransitionAction<PlatformOwner, ReactivatePlatformPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(ReactivatePlatformAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                             ReactivatePlatformPayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Reactivating platform: {}", platform.getId());
        
        // Clear suspension status
        platform.getPlatformStatus().setSuspended(false);
        platform.getPlatformStatus().setSuspensionReason(null);
        
        logger.info("Platform {} reactivated successfully", platform.getId());
    }
}
