package com.handmade.ecommerce.adcampaign.service.cmds;

import com.handmade.ecommerce.adcampaign.model.AdCampaign;
import com.handmade.ecommerce.adcampaign.dto.EndAdCampaignPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;


/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class EndAdCampaignAction extends AbstractSTMTransitionAction<AdCampaign,
		EndAdCampaignPayload>{

	@Override
	public void transitionTo(AdCampaign adtech,
            EndAdCampaignPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}

