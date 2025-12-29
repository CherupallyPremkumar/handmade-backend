package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.dto.ExitMaintenancePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action to exit maintenance mode and return platform to active
 */
public class ExitMaintenanceAction extends AbstractSTMTransitionAction<PlatformOwner, ExitMaintenancePayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(ExitMaintenanceAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                            ExitMaintenancePayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.info("Exiting maintenance mode for platform: {} completed by: {}", 
                   platform.id, payload.getCompletedBy());
        
        // Clear maintenance flag
        platform.suspended = false;
        platform.suspensionReason = null;
        
        logger.info("Platform {} maintenance completed. Notes: {}", 
                   platform.id, 
                   payload.getCompletionNotes());
    }
}
