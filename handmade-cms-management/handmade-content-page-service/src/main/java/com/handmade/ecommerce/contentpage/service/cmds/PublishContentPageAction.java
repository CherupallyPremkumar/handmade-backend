package com.handmade.ecommerce.contentpage.service.cmds;

import com.handmade.ecommerce.cms.dto.PublishContentPagePayload;
import com.handmade.ecommerce.cms.model.ContentPage;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;


/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class PublishContentPageAction extends AbstractSTMTransitionAction<ContentPage,
		PublishContentPagePayload>{

	@Override
	public void transitionTo(ContentPage contentpage,
            PublishContentPagePayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
