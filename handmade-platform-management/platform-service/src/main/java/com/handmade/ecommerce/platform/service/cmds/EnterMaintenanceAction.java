package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.dto.EnterMaintenancePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action to put platform into maintenance mode
 */
public class EnterMaintenanceAction extends AbstractSTMTransitionAction<PlatformOwner, EnterMaintenancePayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(EnterMaintenanceAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                            EnterMaintenancePayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.info("Entering maintenance mode for platform: {} scheduled by: {}", 
                   platform.id, payload.getScheduledBy());
        
        // Set maintenance flag
        platform.suspended = true;
        platform.suspensionReason = "MAINTENANCE: " + payload.getReason();
        
        logger.info("Platform {} in maintenance. Reason: {}, Estimated Duration: {}", 
                   platform.id, 
                   payload.getReason(),
                   payload.getEstimatedDuration());
    }
}
