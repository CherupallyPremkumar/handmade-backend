package com.handmade.ecommerce.payment.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.payment.model.Payment;
import com.handmade.ecommerce.payment.dto.DeactivatePaymentPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class DeactivatePaymentAction extends AbstractSTMTransitionAction<Payment,

    DeactivatePaymentPayload>{


	@Override
	public void transitionTo(Payment payment,
            DeactivatePaymentPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
            payment.transientMap.previousPayload = payload;
	}

}
