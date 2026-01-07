package com.handmade.ecommerce.commission.service.cmds;

import com.handmade.ecommerce.commission.domain.aggregate.CommissionPolicy;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class RemoveCommissionRuleAction extends AbstractSTMTransitionAction<CommissionPolicy, String> {
    @Override
    public void transitionTo(CommissionPolicy policy, String ruleName, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        policy.removeRule(ruleName);
    }
}
