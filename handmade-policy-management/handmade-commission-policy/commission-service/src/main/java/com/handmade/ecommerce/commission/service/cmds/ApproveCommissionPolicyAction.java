package com.handmade.ecommerce.commission.service.cmds;

import com.handmade.ecommerce.commission.domain.aggregate.CommissionPolicy;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import java.time.LocalDate;

/**
 * Action to activate a commission policy
 * Transitions state from DRAFT to ACTIVE
 */
public class ApproveCommissionPolicyAction extends AbstractSTMTransitionAction<CommissionPolicy, Object> {
    @Override
    public void transitionTo(CommissionPolicy policy, Object payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        if (policy.getEffectiveDate() == null) {
            policy.setEffectiveDate(LocalDate.now());
        }
    }
}
