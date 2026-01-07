package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.PerformancePolicy;
import com.handmade.ecommerce.policy.dto.RemovePerformanceThresholdPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class RemovePerformanceThresholdAction extends AbstractSTMTransitionAction<PerformancePolicy, RemovePerformanceThresholdPayload> {
    @Override
    public void transitionTo(PerformancePolicy policy, RemovePerformanceThresholdPayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        policy.removeThreshold(payload.getRuleName());
    }
}
