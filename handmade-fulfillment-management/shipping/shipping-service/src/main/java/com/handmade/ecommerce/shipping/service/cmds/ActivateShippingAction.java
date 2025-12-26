package com.handmade.ecommerce.shipping.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.shipping.model.Shipping;
import com.handmade.ecommerce.shipping.dto.ActivateShippingPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ActivateShippingAction extends AbstractSTMTransitionAction<Shipping,

    ActivateShippingPayload>{


	@Override
	public void transitionTo(Shipping shipping,
            ActivateShippingPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
            shipping.transientMap.previousPayload = payload;
	}

}
