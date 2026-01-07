package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.ReturnPolicy;
import com.handmade.ecommerce.policy.domain.entity.ReturnPolicyRule;
import com.handmade.ecommerce.policy.dto.AddReturnRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class AddReturnRuleAction extends AbstractSTMTransitionAction<ReturnPolicy, AddReturnRulePayload> {
    @Override
    public void transitionTo(ReturnPolicy policy, AddReturnRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        ReturnPolicyRule rule = new ReturnPolicyRule();
        rule.setRuleName(payload.getRuleName());
        rule.setViolationType(payload.getViolationType());
        rule.setRequired(payload.getRequired());
        rule.setThresholdValue(payload.getThresholdValue());
        policy.addRule(rule);
    }
}
