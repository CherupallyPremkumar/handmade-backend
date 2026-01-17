package com.handmade.ecommerce.platform.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.platform.model.Platform;
import com.handmade.ecommerce.platform.model.SuspendPlatformPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class SuspendPlatformAction extends AbstractSTMTransitionAction<Platform,
    SuspendPlatformPayload>{

	@Override
	public void transitionTo(Platform platform,
            SuspendPlatformPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
