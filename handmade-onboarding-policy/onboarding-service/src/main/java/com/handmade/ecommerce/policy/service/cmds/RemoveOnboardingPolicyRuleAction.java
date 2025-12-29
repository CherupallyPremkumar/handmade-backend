package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import com.handmade.ecommerce.policy.dto.RemoveOnboardingPolicyRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Remove Rule from Onboarding Policy Action
 * Can only be performed in DRAFT state.
 */
public class RemoveOnboardingPolicyRuleAction extends AbstractSTMTransitionAction<OnboardingPolicy, RemoveOnboardingPolicyRulePayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(RemoveOnboardingPolicyRuleAction.class);
    
    @Override
    public void transitionTo(OnboardingPolicy policy,
                             RemoveOnboardingPolicyRulePayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Removing rule from onboarding policy: {}", policy.getId());
        
        Optional<OnboardingPolicyRule> ruleOpt = policy.getRules().stream()
                .filter(r -> r.getStepName().equals(payload.stepName))
                .findFirst();
        
        if (ruleOpt.isPresent()) {
            policy.getRules().remove(ruleOpt.get());
            logger.info("Rule {} removed from onboarding policy {}", payload.stepName, policy.getId());
        } else {
            logger.warn("Rule {} not found in onboarding policy {}", payload.stepName, policy.getId());
        }
    }
}
