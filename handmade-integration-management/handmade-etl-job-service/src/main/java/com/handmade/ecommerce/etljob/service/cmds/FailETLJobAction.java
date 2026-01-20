package com.handmade.ecommerce.etljob.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.integration.model.ETLJob;
import com.handmade.ecommerce.integration.dto.FailEtlJobPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class FailETLJobAction extends AbstractSTMTransitionAction<ETLJob,
               FailEtlJobPayload>{

	@Override
	public void transitionTo(ETLJob etljob,
            FailEtlJobPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
