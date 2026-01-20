package com.handmade.ecommerce.sellerverification.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.onboarding.model.SellerVerification;
import com.handmade.ecommerce.onboarding.dto.SubmitSellerVerificationPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class SubmitSellerVerificationAction extends AbstractSTMTransitionAction<SellerVerification,
               SubmitSellerVerificationPayload>{

	@Override
	public void transitionTo(SellerVerification sellerverification,
            SubmitSellerVerificationPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
