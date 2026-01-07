package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.InventoryPolicy;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class ApproveInventoryPolicyAction extends AbstractSTMTransitionAction<InventoryPolicy, Object> {
    @Override
    public void transitionTo(InventoryPolicy policy, Object payload, State startState, 
                             String eventId, State endState,  
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        policy.approve("SYSTEM");
    }
}
