package com.handmade.ecommerce.paymenttransaction.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.payment.model.PaymentTransaction;
import com.handmade.ecommerce.payment.dto.ProcessPaymentTransactionPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ProcessPaymentTransactionAction extends AbstractSTMTransitionAction<PaymentTransaction,
               ProcessPaymentTransactionPayload>{

	@Override
	public void transitionTo(PaymentTransaction paymenttransaction,
            ProcessPaymentTransactionPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
