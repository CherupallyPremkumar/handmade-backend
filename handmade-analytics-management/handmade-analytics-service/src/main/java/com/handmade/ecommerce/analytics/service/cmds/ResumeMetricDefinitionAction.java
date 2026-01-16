package com.handmade.ecommerce.analytics.service.cmds;

import com.handmade.ecommerce.analytics.dto.ResumeMetricDefinitionPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.analytics.model.MetricDefinition;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ResumeMetricDefinitionAction extends AbstractSTMTransitionAction<MetricDefinition,
		ResumeMetricDefinitionPayload>{

	@Override
	public void transitionTo(MetricDefinition analytics,
            ResumeMetricDefinitionPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
