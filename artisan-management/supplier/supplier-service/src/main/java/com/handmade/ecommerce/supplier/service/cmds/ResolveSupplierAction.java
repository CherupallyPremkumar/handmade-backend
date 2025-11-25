package com.handmade.ecommerce.supplier.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.param.MinimalPayload;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.supplier.model.Supplier;

public class ResolveSupplierAction extends AbstractSTMTransitionAction<Supplier,MinimalPayload>{

	@Override
	public void transitionTo(Supplier supplier, MinimalPayload payload, State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
		supplier.resolveComment = payload.getComment();
	}

}
