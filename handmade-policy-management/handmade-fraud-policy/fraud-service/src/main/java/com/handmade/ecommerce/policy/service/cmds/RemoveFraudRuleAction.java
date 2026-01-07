package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.FraudPolicy;
import com.handmade.ecommerce.policy.dto.RemoveFraudRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class RemoveFraudRuleAction extends AbstractSTMTransitionAction<FraudPolicy, RemoveFraudRulePayload> {
    @Override
    public void transitionTo(FraudPolicy policy, RemoveFraudRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        policy.removeRule(payload.getRuleName());
    }
}
