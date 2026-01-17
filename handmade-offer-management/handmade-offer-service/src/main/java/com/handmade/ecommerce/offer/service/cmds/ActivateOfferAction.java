package com.handmade.ecommerce.offer.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.offer.model.Offer;
import com.handmade.ecommerce.offer.model.ActivateOfferPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ActivateOfferAction extends AbstractSTMTransitionAction<Offer,
    ActivateOfferPayload>{

	@Override
	public void transitionTo(Offer offer,
            ActivateOfferPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
package com.handmade.ecommerce.offer.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.offer.model.Offer;
import com.handmade.ecommerce.offer.model.ActivateOfferPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ActivateOfferAction extends AbstractSTMTransitionAction<Offer,
    ActivateOfferPayload>{

	@Override
	public void transitionTo(Offer offer,
            ActivateOfferPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
