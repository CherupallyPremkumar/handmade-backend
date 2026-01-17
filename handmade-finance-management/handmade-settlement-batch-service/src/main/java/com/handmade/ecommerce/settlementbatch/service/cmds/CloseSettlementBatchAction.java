package com.handmade.ecommerce.settlementbatch.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.finance.model.SettlementBatch;
import com.handmade.ecommerce.finance.model. CloseSettlementBatchPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class CloseSettlementBatchAction extends AbstractSTMTransitionAction<SettlementBatch,
    CloseSettlementBatchPayload>{

	@Override
	public void transitionTo(SettlementBatch settlementbatch,
            CloseSettlementBatchPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
