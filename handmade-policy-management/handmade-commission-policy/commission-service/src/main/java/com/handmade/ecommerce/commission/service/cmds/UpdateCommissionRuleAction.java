package com.handmade.ecommerce.commission.service.cmds;

import com.handmade.ecommerce.commission.domain.aggregate.CommissionPolicy;
import com.handmade.ecommerce.commission.domain.entity.CommissionRule;
import com.handmade.ecommerce.commission.dto.UpdateCommissionRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class UpdateCommissionRuleAction extends AbstractSTMTransitionAction<CommissionPolicy, UpdateCommissionRulePayload> {
    @Override
    public void transitionTo(CommissionPolicy policy, UpdateCommissionRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        CommissionRule rule = new CommissionRule();
        rule.setRuleName(payload.getRuleName());
        rule.setFeeType(payload.getFeeType());
        rule.setThresholdValue(payload.getThresholdValue());
        rule.setRequired(payload.getRequired());
        
        policy.updateRule(rule);
    }
}
