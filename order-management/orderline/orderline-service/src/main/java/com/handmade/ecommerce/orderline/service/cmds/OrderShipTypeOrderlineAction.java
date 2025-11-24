package com.handmade.ecommerce.orderline.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.orderline.model.Orderline;
import com.handmade.ecommerce.orderline.dto.OrderShipTypeOrderlinePayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class OrderShipTypeOrderlineAction extends AbstractSTMTransitionAction<Orderline,

    OrderShipTypeOrderlinePayload>{


	@Override
	public void transitionTo(Orderline orderline,
            OrderShipTypeOrderlinePayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
            orderline.transientMap.previousPayload = payload;
	}

}
