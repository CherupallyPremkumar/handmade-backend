package com.handmade.ecommerce.order.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.order.model.Order;
import com.handmade.ecommerce.order.dto.AddOrderPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class AddOrderAction extends AbstractSTMTransitionAction<Order,

    AddOrderPayload>{


	@Override
	public void transitionTo(Order order,
            AddOrderPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
            order.transientMap.previousPayload = payload;
	}

}
