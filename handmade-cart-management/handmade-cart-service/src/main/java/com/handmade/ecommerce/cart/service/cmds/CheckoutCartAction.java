package com.handmade.ecommerce.cart.service.cmds;

import com.handmade.ecommerce.cart.dto.CheckoutCartPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.cart.model.Cart;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class CheckoutCartAction extends AbstractSTMTransitionAction<Cart,
		CheckoutCartPayload>{

	@Override
	public void transitionTo(Cart cart,
            CheckoutCartPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
