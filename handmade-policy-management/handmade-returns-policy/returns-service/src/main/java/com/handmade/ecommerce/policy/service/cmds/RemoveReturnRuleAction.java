package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.ReturnPolicy;
import com.handmade.ecommerce.policy.dto.RemoveReturnRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class RemoveReturnRuleAction extends AbstractSTMTransitionAction<ReturnPolicy, RemoveReturnRulePayload> {
    @Override
    public void transitionTo(ReturnPolicy policy, RemoveReturnRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        policy.removeRule(payload.getRuleName());
    }
}
