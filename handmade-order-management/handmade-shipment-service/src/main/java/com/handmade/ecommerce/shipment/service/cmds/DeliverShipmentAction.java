package com.handmade.ecommerce.shipment.service.cmds;

import com.handmade.ecommerce.order.dto.DeliverShipmentPayload;
import com.handmade.ecommerce.order.model.Shipment;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;


/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class DeliverShipmentAction extends AbstractSTMTransitionAction<Shipment,
		DeliverShipmentPayload>{

	@Override
	public void transitionTo(Shipment shipment,
            DeliverShipmentPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
