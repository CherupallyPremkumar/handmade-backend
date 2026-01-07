package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.ReturnPolicy;
import com.handmade.ecommerce.policy.dto.UpdateReturnRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

import com.handmade.ecommerce.policy.domain.entity.ReturnPolicyRule;

public class UpdateReturnRuleAction extends AbstractSTMTransitionAction<ReturnPolicy, UpdateReturnRulePayload> {
    @Override
    public void transitionTo(ReturnPolicy policy, UpdateReturnRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        ReturnPolicyRule rule = new ReturnPolicyRule();
        rule.setRuleName(payload.getRuleName());
        rule.setViolationType(payload.getViolationType());
        rule.setRequired(payload.getRequired());
        rule.setThresholdValue(payload.getThresholdValue());
        policy.updateRule(rule);
    }
}
