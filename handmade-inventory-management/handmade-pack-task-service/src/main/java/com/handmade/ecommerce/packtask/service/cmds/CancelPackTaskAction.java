package com.handmade.ecommerce.packtask.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.inventory.model.PackTask;
import com.handmade.ecommerce.inventory.model. CancelPackTaskPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class CancelPackTaskAction extends AbstractSTMTransitionAction<PackTask,
    CancelPackTaskPayload>{

	@Override
	public void transitionTo(PackTask packtask,
            CancelPackTaskPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
