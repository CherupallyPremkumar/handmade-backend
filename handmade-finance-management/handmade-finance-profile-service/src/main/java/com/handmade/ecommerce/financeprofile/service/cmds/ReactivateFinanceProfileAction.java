package com.handmade.ecommerce.financeprofile.service.cmds;

import com.handmade.ecommerce.finance.dto.ReactivateFinanceProfilePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.finance.model.FinanceProfile;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ReactivateFinanceProfileAction extends AbstractSTMTransitionAction<FinanceProfile,
		ReactivateFinanceProfilePayload>{

	@Override
	public void transitionTo(FinanceProfile financeprofile,
            ReactivateFinanceProfilePayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
