package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.InventoryPolicy;
import com.handmade.ecommerce.policy.dto.RemoveInventoryRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class RemoveInventoryRuleAction extends AbstractSTMTransitionAction<InventoryPolicy, RemoveInventoryRulePayload> {
    @Override
    public void transitionTo(InventoryPolicy policy, RemoveInventoryRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        policy.removeRule(payload.getRuleName());
    }
}
