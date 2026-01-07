package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Update Onboarding Policy Description Action
 * Can be performed in DRAFT or ACTIVE state.
 */
public class UpdateOnboardingPolicyDescriptionAction extends AbstractSTMTransitionAction<OnboardingPolicy, String> {
    
    private static final Logger logger = LoggerFactory.getLogger(UpdateOnboardingPolicyDescriptionAction.class);
    
    @Override
    public void transitionTo(OnboardingPolicy policy,
                             String description,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Updating description for onboarding policy: {}", policy.getId());
        
        policy.setDescription(description);
        
        logger.info("Description updated for onboarding policy {}", policy.getId());
    }
}
