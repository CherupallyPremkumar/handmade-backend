package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.PricingPolicy;
import com.handmade.ecommerce.policy.domain.entity.PricingPolicyRule;
import org.chenile.stm.STMInternalTransitionInvoker;
import com.handmade.ecommerce.policy.dto.UpdatePricingRulePayload;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class UpdatePricingRuleAction extends AbstractSTMTransitionAction<PricingPolicy, UpdatePricingRulePayload> {
    @Override
    public void transitionTo(PricingPolicy policy, UpdatePricingRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        PricingPolicyRule rule = new PricingPolicyRule();
        rule.setRuleName(payload.getRuleName());
        rule.setViolationType(payload.getViolationType());
        rule.setRequired(payload.getRequired());
        rule.setThresholdValue(payload.getThresholdValue());
        policy.updateRule(rule);
    }
}
