package com.handmade.ecommerce.reviews.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.reviews.model.Reviews;
import com.handmade.ecommerce.reviews.model. ArchiveReviewsPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ArchiveReviewsAction extends AbstractSTMTransitionAction<Reviews,
    ArchiveReviewsPayload>{

	@Override
	public void transitionTo(Reviews reviews,
            ArchiveReviewsPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
