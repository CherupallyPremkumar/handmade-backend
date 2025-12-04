package com.handmade.ecommerce.cartline.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

import com.handmade.ecommerce.cartline.model.AssignCartlinePayload;
import com.handmade.ecommerce.cartline.model.Cartline;

/**
    This class implements the assign action. It will need to inherit from the AbstractSTMTransitionAction for
    auto wiring into STM to work properly. Be sure to use the PayloadType as the second argument and
    override the transitionTo method accordingly as shown below.
*/
public class AssignCartlineAction extends AbstractSTMTransitionAction<Cartline,AssignCartlinePayload>{

	@Override
	public void transitionTo(Cartline cartline, AssignCartlinePayload payload, State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
		cartline.assignee = payload.assignee;
		cartline.assignComment = payload.getComment();
	}

}
