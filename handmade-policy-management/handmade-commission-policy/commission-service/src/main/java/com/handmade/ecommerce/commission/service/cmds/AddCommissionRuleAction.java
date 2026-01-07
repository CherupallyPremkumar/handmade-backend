package com.handmade.ecommerce.commission.service.cmds;

import com.handmade.ecommerce.commission.domain.aggregate.CommissionPolicy;
import com.handmade.ecommerce.commission.domain.entity.CommissionRule;
import com.handmade.ecommerce.commission.dto.AddCommissionRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class AddCommissionRuleAction extends AbstractSTMTransitionAction<CommissionPolicy, AddCommissionRulePayload> {
    @Override
    public void transitionTo(CommissionPolicy policy, AddCommissionRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        CommissionRule rule = new CommissionRule();
        rule.setRuleName(payload.getRuleName());
        rule.setFeeType(payload.getFeeType());
        rule.setThresholdValue(payload.getThresholdValue());
        rule.setRequired(payload.getRequired());
        
        policy.addRule(rule);
    }
}
