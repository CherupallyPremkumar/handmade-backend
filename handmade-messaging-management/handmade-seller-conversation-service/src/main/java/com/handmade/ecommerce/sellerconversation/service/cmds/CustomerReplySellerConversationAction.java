package com.handmade.ecommerce.sellerconversation.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.messaging.model.SellerConversation;
import com.handmade.ecommerce.messaging.model. CustomerReplySellerConversationPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class CustomerReplySellerConversationAction extends AbstractSTMTransitionAction<SellerConversation,
    CustomerReplySellerConversationPayload>{

	@Override
	public void transitionTo(SellerConversation sellerconversation,
            CustomerReplySellerConversationPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
