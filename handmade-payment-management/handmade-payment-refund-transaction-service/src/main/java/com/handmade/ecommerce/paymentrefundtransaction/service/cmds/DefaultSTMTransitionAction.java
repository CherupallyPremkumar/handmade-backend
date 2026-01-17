package com.handmade.ecommerce.paymentrefundtransaction.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.param.MinimalPayload;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.payment.model.PaymentRefundTransaction;

/**
    This class is invoked if no specific transition action is specified
    Extend this class to do generic things that are relevant for all actions in the workflow
*/

public class DefaultSTMTransitionAction<PayloadType extends MinimalPayload>
    extends AbstractSTMTransitionAction<PaymentRefundTransaction, PayloadType> {
    @Override
    public void transitionTo(PaymentRefundTransaction paymentrefundtransaction, PayloadType payload,
                 State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm,
                 Transition transition) {

    }
}