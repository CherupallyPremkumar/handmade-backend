package com.handmade.ecommerce.cyclecount.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.inventory.model.CycleCount;
import com.handmade.ecommerce.inventory.dto.DetectVarianceCycleCountPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class DetectVarianceCycleCountAction extends AbstractSTMTransitionAction<CycleCount,
               DetectVarianceCycleCountPayload>{

	@Override
	public void transitionTo(CycleCount cyclecount,
            DetectVarianceCycleCountPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
