package com.handmade.ecommerce.experiment.service.cmds;

import com.handmade.ecommerce.experiment.dto.ActivateExperimentDefinitionPayload;
import com.handmade.ecommerce.experiment.model.ExperimentDefinition;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ActivateExperimentDefinitionAction extends AbstractSTMTransitionAction<ExperimentDefinition,
		ActivateExperimentDefinitionPayload>{

	@Override
	public void transitionTo(ExperimentDefinition experiment,
							 ActivateExperimentDefinitionPayload payload,
							 State startState, String eventId,
							 State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
