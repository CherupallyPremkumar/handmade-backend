package com.handmade.ecommerce.fraudcase.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.risk.model.FraudCase;
import com.handmade.ecommerce.risk.model. AssignFraudCasePayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class AssignFraudCaseAction extends AbstractSTMTransitionAction<FraudCase,
    AssignFraudCasePayload>{

	@Override
	public void transitionTo(FraudCase fraudcase,
            AssignFraudCasePayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
