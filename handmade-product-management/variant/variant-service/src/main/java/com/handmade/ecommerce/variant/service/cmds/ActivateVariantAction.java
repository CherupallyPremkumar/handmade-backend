package com.handmade.ecommerce.variant.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.variant.model.Variant;
import com.handmade.ecommerce.variant.dto.ActivateVariantPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ActivateVariantAction extends AbstractSTMTransitionAction<Variant,

    ActivateVariantPayload>{


	@Override
	public void transitionTo(Variant variant,
            ActivateVariantPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
            variant.transientMap.previousPayload = payload;
	}

}
