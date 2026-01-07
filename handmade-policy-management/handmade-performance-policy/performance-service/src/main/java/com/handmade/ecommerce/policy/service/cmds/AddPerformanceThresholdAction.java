package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.PerformancePolicy;
import com.handmade.ecommerce.policy.domain.entity.PerformanceThreshold;
import com.handmade.ecommerce.policy.dto.AddPerformanceThresholdPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class AddPerformanceThresholdAction extends AbstractSTMTransitionAction<PerformancePolicy, AddPerformanceThresholdPayload> {
    @Override
    public void transitionTo(PerformancePolicy policy, AddPerformanceThresholdPayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        PerformanceThreshold threshold = new PerformanceThreshold();
        threshold.setViolationType(payload.getViolationType());
        threshold.setWarningThreshold(payload.getWarningThreshold());
        threshold.setCriticalThreshold(payload.getCriticalThreshold());
        threshold.setRequired(payload.getRequired());
        policy.addThreshold(threshold);
    }
}
