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
 * Deprecate Onboarding Policy Action
 * Transitions policy from ACTIVE to DEPRECATED state
 */
public class DeprecateOnboardingPolicyAction extends AbstractSTMTransitionAction<OnboardingPolicy, MinimalPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(DeprecateOnboardingPolicyAction.class);
    
    @Override
    public void transitionTo(OnboardingPolicy policy,
                             MinimalPayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Deprecating onboarding policy: {}", policy.getId());
        
        policy.deprecate();
        
        logger.info("Onboarding policy {} deprecated successfully", policy.getId());
    }
}
