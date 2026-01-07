package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.dto.ApproveOnboardingPolicyPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Approve Onboarding Policy Action
 * Transitions policy from DRAFT to ACTIVE state
 */
public class ApproveOnboardingPolicyAction extends AbstractSTMTransitionAction<OnboardingPolicy, ApproveOnboardingPolicyPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(ApproveOnboardingPolicyAction.class);
    
    @Override
    public void transitionTo(OnboardingPolicy policy,
                             ApproveOnboardingPolicyPayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Approving onboarding policy: {}", policy.getId());
        
        if (payload != null) {
            policy.approve(payload.approvedBy);
        } else {
            policy.approve("SYSTEM");
        }
        
        logger.info("Onboarding policy {} approved successfully", policy.getId());
    }
}
