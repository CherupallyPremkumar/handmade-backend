package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Set Onboarding Policy Effective To Action
 * Can be performed in ACTIVE state to schedule deprecation or end-of-use.
 */
public class SetOnboardingPolicyEffectiveToAction extends AbstractSTMTransitionAction<OnboardingPolicy, LocalDate> {
    
    private static final Logger logger = LoggerFactory.getLogger(SetOnboardingPolicyEffectiveToAction.class);
    
    @Override
    public void transitionTo(OnboardingPolicy policy,
                             LocalDate effectiveToDate,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Setting effectiveTo date for onboarding policy: {}", policy.getId());
        
        policy.setDeprecatedDate(effectiveToDate);
        
        logger.info("EffectiveTo date set to {} for onboarding policy {}", effectiveToDate, policy.getId());
    }
}
