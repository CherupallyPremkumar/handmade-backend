package com.handmade.ecommerce.orderline.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.orderline.model.Orderline;
import com.handmade.ecommerce.orderline.dto.DeleteOrderLineOrderlinePayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class DeleteOrderLineOrderlineAction extends AbstractSTMTransitionAction<Orderline,

    DeleteOrderLineOrderlinePayload>{


	@Override
	public void transitionTo(Orderline orderline,
            DeleteOrderLineOrderlinePayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
            orderline.transientMap.previousPayload = payload;
	}

}
