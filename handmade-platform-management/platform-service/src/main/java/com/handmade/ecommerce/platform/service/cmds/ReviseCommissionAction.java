package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.dto.ReviseCommissionPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Revise Commission Action
 * Self-transition in ACTIVE state to update commission policy
 */
public class ReviseCommissionAction extends AbstractSTMTransitionAction<PlatformOwner, ReviseCommissionPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(ReviseCommissionAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                             ReviseCommissionPayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Revising commission policy for platform: {}", platform.id);
        
        // Update commission policy reference
        platform.activeCommissionPolicyId = payload.newCommissionPolicyId;
        
        logger.info("Commission policy revised for platform {}", platform.id);
    }
}
