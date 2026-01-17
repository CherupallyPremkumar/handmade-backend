package com.handmade.ecommerce.paymentrefundtransaction.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.payment.model.PaymentRefundTransaction;
import com.handmade.ecommerce.payment.model. CancelPaymentRefundTransactionPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class CancelPaymentRefundTransactionAction extends AbstractSTMTransitionAction<PaymentRefundTransaction,
    CancelPaymentRefundTransactionPayload>{

	@Override
	public void transitionTo(PaymentRefundTransaction paymentrefundtransaction,
            CancelPaymentRefundTransactionPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
