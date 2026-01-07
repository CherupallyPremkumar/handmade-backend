package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.FraudPolicy;
import com.handmade.ecommerce.policy.domain.entity.FraudPolicyRule;
import com.handmade.ecommerce.policy.dto.AddFraudRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class AddFraudRuleAction extends AbstractSTMTransitionAction<FraudPolicy, AddFraudRulePayload> {
    @Override
    public void transitionTo(FraudPolicy policy, AddFraudRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        FraudPolicyRule rule = new FraudPolicyRule();
        rule.setRuleName(payload.getRuleName());
        rule.setViolationType(payload.getViolationType());
        rule.setRequired(payload.getRequired());
        rule.setThresholdValue(payload.getThresholdValue());
        policy.addRule(rule);
    }
}
