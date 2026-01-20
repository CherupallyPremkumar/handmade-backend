package com.handmade.ecommerce.inventoryreservation.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.inventory.model.InventoryReservation;
import com.handmade.ecommerce.inventory.dto.CancelInventoryReservationPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class CancelInventoryReservationAction extends AbstractSTMTransitionAction<InventoryReservation,
               CancelInventoryReservationPayload>{

	@Override
	public void transitionTo(InventoryReservation inventoryreservation,
            CancelInventoryReservationPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
