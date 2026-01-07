package com.handmade.ecommerce.finance.service.cmds;

import com.handmade.ecommerce.finance.domain.aggregate.PayoutInstruction;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

/**
 * Action to handle payout failure and potentially revert reserves
 */
public class PayoutFailAction extends AbstractSTMTransitionAction<PayoutInstruction, String> {
    @Override
    public void transitionTo(PayoutInstruction payout, String reason, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Log failure reason
    }
}
