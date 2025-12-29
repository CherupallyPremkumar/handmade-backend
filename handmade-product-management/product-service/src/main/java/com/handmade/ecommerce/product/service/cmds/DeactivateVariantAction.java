package com.handmade.ecommerce.product.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.product.domain.model.Variant;
import com.handmade.ecommerce.product.dto.DeactivateVariantPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class DeactivateVariantAction extends AbstractSTMTransitionAction<Variant,

    DeactivateVariantPayload>{


	@Override
	public void transitionTo(Variant variant,
            DeactivateVariantPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
            variant.transientMap.previousPayload = payload;
	}

}
