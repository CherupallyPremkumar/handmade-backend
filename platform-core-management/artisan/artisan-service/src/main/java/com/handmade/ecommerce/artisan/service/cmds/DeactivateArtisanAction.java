package com.handmade.ecommerce.artisan.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.artisan.model.Artisan;
import com.handmade.ecommerce.artisan.dto.DeactivateArtisanPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class DeactivateArtisanAction extends AbstractSTMTransitionAction<Artisan,

    DeactivateArtisanPayload>{


	@Override
	public void transitionTo(Artisan artisan,
            DeactivateArtisanPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
            artisan.transientMap.previousPayload = payload;
	}

}
