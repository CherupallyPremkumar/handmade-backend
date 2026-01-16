package com.handmade.ecommerce.eventqueue.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.event.model.EventQueue;
import com.handmade.ecommerce.event.model.FailEventQueuePayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class FailEventQueueAction extends AbstractSTMTransitionAction<EventQueue,
    FailEventQueuePayload>{

	@Override
	public void transitionTo(EventQueue eventqueue,
            FailEventQueuePayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
