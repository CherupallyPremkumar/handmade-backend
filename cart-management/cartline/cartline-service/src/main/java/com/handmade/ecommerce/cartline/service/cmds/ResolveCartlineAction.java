package com.handmade.ecommerce.cartline.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.param.MinimalPayload;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.cartline.model.Cartline;

public class ResolveCartlineAction extends AbstractSTMTransitionAction<Cartline,MinimalPayload>{

	@Override
	public void transitionTo(Cartline cartline, MinimalPayload payload, State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
		cartline.resolveComment = payload.getComment();
	}

}
