package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.param.MinimalPayload;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Delete Onboarding Policy Action
 * Transitions draft policy to DELETED state (terminal)
 */
public class DeleteOnboardingPolicyAction extends AbstractSTMTransitionAction<OnboardingPolicy, MinimalPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(DeleteOnboardingPolicyAction.class);
    
    @Override
    public void transitionTo(OnboardingPolicy policy,
                             MinimalPayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Deleteting draft onboarding policy: {}", policy.getId());
        
        // Logical deletion or metadata update as needed
        // State transition to DELETED is handled by STM
        
        logger.info("Onboarding policy {} marked as DELETED", policy.getId());
    }
}
