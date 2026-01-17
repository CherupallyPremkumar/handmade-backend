package com.handmade.ecommerce.limitdefinition.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.limit.model.LimitDefinition;
import com.handmade.ecommerce.limit.model. SuspendLimitDefinitionPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class SuspendLimitDefinitionAction extends AbstractSTMTransitionAction<LimitDefinition,
    SuspendLimitDefinitionPayload>{

	@Override
	public void transitionTo(LimitDefinition limitdefinition,
            SuspendLimitDefinitionPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
