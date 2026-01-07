package com.handmade.ecommerce.finance.service.cmds;

import com.handmade.ecommerce.finance.domain.aggregate.PayoutInstruction;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

/**
 * Action to initiate the external payout process (e.g., Stripe Connect)
 */
public class InitiatePayoutAction extends AbstractSTMTransitionAction<PayoutInstruction, Object> {
    @Override
    public void transitionTo(PayoutInstruction payout, Object payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Logic to call External Payment Provider (Stripe/Adyen) would go here
        // For now, we just track that processing has started
    }
}
