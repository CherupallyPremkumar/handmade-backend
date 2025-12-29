package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import com.handmade.ecommerce.policy.dto.AddOnboardingPolicyRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Add Rule to Onboarding Policy Action
 * Can only be performed in DRAFT state.
 */
public class AddOnboardingPolicyRuleAction extends AbstractSTMTransitionAction<OnboardingPolicy, AddOnboardingPolicyRulePayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(AddOnboardingPolicyRuleAction.class);
    
    @Override
    public void transitionTo(OnboardingPolicy policy,
                             AddOnboardingPolicyRulePayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Adding rule to onboarding policy: {}", policy.getId());
        
        OnboardingPolicyRule rule = new OnboardingPolicyRule();
        rule.setStepName(payload.stepName);
        rule.setStepOrder(payload.stepOrder);
        rule.setRequired(payload.required);
        rule.setProviderType(payload.providerType);
        rule.setProviderConfig(payload.providerConfig);
        rule.setMaxRetries(payload.maxRetries);
        rule.setRetryDelayHours(payload.retryDelayHours);
        rule.setMaxDurationDays(payload.maxDurationDays);
        rule.setPolicyId(policy.getId());
        
        policy.getRules().add(rule);
        
        logger.info("Rule {} added to onboarding policy {}", payload.stepName, policy.getId());
    }
}
