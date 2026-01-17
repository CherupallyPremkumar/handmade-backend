package com.handmade.ecommerce.inventoryreservation.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.inventory.model.InventoryReservation;
import com.handmade.ecommerce.inventory.model. ConfirmInventoryReservationPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ConfirmInventoryReservationAction extends AbstractSTMTransitionAction<InventoryReservation,
    ConfirmInventoryReservationPayload>{

	@Override
	public void transitionTo(InventoryReservation inventoryreservation,
            ConfirmInventoryReservationPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
