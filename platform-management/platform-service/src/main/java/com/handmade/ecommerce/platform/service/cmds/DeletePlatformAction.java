package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.dto.DeletePlatformPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Delete Platform Action
 * Soft deletes the platform (terminal state)
 */
public class DeletePlatformAction extends AbstractSTMTransitionAction<PlatformOwner, DeletePlatformPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(DeletePlatformAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                             DeletePlatformPayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.warn("Deleting platform: {} - Reason: {}", platform.getId(), payload.getReason());
        
        // Soft delete - mark as deleted
        platform.setDeleted(true);
        platform.setDeletedAt(java.time.LocalDateTime.now());
        platform.setDeletedBy(payload.getDeletedBy());
        
        logger.info("Platform {} deleted successfully", platform.getId());
    }
}
