package com.handmade.ecommerce.analytics.service.cmds;

import com.handmade.ecommerce.analytics.dto.PauseMetricDefinitionPayload;
import com.handmade.ecommerce.analytics.model.MetricDefinition;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;


/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class PauseMetricDefinitionAction extends AbstractSTMTransitionAction<MetricDefinition,
		PauseMetricDefinitionPayload>{

	@Override
	public void transitionTo(MetricDefinition analytics,
            PauseMetricDefinitionPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
