package com.handmade.ecommerce.supplier.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

import com.handmade.ecommerce.supplier.model.AssignSupplierPayload;
import com.handmade.ecommerce.supplier.model.Supplier;

/**
    This class implements the assign action. It will need to inherit from the AbstractSTMTransitionAction for
    auto wiring into STM to work properly. Be sure to use the PayloadType as the second argument and
    override the transitionTo method accordingly as shown below.
*/
public class AssignSupplierAction extends AbstractSTMTransitionAction<Supplier,AssignSupplierPayload>{

	@Override
	public void transitionTo(Supplier supplier, AssignSupplierPayload payload, State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
		supplier.assignee = payload.assignee;
		supplier.assignComment = payload.getComment();
	}

}
