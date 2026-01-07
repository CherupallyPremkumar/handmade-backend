package com.handmade.ecommerce.finance.service.cmds;

import com.handmade.ecommerce.finance.domain.aggregate.PayoutInstruction;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import java.time.LocalDateTime;

/**
 * Action to mark the payout as successfully completed
 */
public class CompletePayoutAction extends AbstractSTMTransitionAction<PayoutInstruction, String> {
    @Override
    public void transitionTo(PayoutInstruction payout, String externalId, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        payout.setExternalPayoutId(externalId);
        payout.setCompletedAt(LocalDateTime.now());
    }
}
