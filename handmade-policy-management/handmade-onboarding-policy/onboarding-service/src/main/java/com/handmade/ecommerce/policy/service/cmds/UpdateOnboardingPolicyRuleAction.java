package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import com.handmade.ecommerce.policy.dto.UpdateOnboardingPolicyRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Update Rule in Onboarding Policy Action
 * Can only be performed in DRAFT state.
 */
public class UpdateOnboardingPolicyRuleAction extends AbstractSTMTransitionAction<OnboardingPolicy, UpdateOnboardingPolicyRulePayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(UpdateOnboardingPolicyRuleAction.class);
    
    @Override
    public void transitionTo(OnboardingPolicy policy,
                             UpdateOnboardingPolicyRulePayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Updating rule in onboarding policy: {}", policy.getId());
        
        Optional<OnboardingPolicyRule> ruleOpt = policy.getRules().stream()
                .filter(r -> r.getStepName().equals(payload.stepName))
                .findFirst();
        
        if (ruleOpt.isPresent()) {
            OnboardingPolicyRule rule = ruleOpt.get();
            if (payload.stepOrder != null) rule.setStepOrder(payload.stepOrder);
            if (payload.required != null) rule.setRequired(payload.required);
            if (payload.providerType != null) rule.setProviderType(payload.providerType);
            if (payload.providerConfig != null) rule.setProviderConfig(payload.providerConfig);
            if (payload.maxRetries != null) rule.setMaxRetries(payload.maxRetries);
            if (payload.retryDelayHours != null) rule.setRetryDelayHours(payload.retryDelayHours);
            if (payload.maxDurationDays != null) rule.setMaxDurationDays(payload.maxDurationDays);
            
            logger.info("Rule {} updated in onboarding policy {}", payload.stepName, policy.getId());
        } else {
            logger.error("Rule {} not found in onboarding policy {}", payload.stepName, policy.getId());
            throw new RuntimeException("Rule not found: " + payload.stepName);
        }
    }
}
