package com.handmade.ecommerce.platform.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.platform.model.Platform;
import com.handmade.ecommerce.platform.model. ResumePlatformPlatformPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ResumePlatformPlatformAction extends AbstractSTMTransitionAction<Platform,
    ResumePlatformPlatformPayload>{

	@Override
	public void transitionTo(Platform platform,
            ResumePlatformPlatformPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
package com.handmade.ecommerce.platform.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.platform.model.Platform;
import com.handmade.ecommerce.platform.model. ResumePlatformPlatformPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ResumePlatformPlatformAction extends AbstractSTMTransitionAction<Platform,
    ResumePlatformPlatformPayload>{

	@Override
	public void transitionTo(Platform platform,
            ResumePlatformPlatformPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
