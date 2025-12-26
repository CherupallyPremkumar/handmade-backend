package com.handmade.ecommerce.user.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.user.model.User;
import com.handmade.ecommerce.user.dto.SuspendUserPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class SuspendUserAction extends AbstractSTMTransitionAction<User,

    SuspendUserPayload>{


	@Override
	public void transitionTo(User user,
            SuspendUserPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
            user.transientMap.previousPayload = payload;
	}

}
