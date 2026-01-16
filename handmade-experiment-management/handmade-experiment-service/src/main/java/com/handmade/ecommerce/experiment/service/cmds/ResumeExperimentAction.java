package com.handmade.ecommerce.experiment.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.experiment.model.Experiment;
import com.handmade.ecommerce.experiment.model. ResumeExperimentPayload;

/**
 Contains customized logic for the transition. Common logic resides at {@link DefaultSTMTransitionAction}
 <p>Use this class if you want to augment the common logic for this specific transition</p>
 <p>Use a customized payload if required instead of MinimalPayload</p>
*/
public class ResumeExperimentAction extends AbstractSTMTransitionAction<Experiment,
    ResumeExperimentPayload>{

	@Override
	public void transitionTo(Experiment experiment,
            ResumeExperimentPayload payload,
            State startState, String eventId,
			State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
	}

}
