package com.handmade.ecommerce.selleraccount.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.model.SellerAccount;
import com.handmade.ecommerce.seller.dto.VerifySellerAccountPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class VerifySellerAccountAction extends AbstractSTMTransitionAction<SellerAccount,
               VerifySellerAccountPayload>{

	@Override
	public void transitionTo(SellerAccount selleraccount,
            VerifySellerAccountPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
