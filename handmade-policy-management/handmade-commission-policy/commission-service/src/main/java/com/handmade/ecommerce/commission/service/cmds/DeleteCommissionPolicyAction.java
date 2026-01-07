package com.handmade.ecommerce.commission.service.cmds;

import com.handmade.ecommerce.commission.domain.aggregate.CommissionPolicy;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

/**
 * Action to delete a commission policy (typically in DRAFT)
 */
public class DeleteCommissionPolicyAction extends AbstractSTMTransitionAction<CommissionPolicy, Object> {
    @Override
    public void transitionTo(CommissionPolicy policy, Object payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Soft delete logic if needed
    }
}
