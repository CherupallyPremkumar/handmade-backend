package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.PerformancePolicy;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class DeletePerformancePolicyAction extends AbstractSTMTransitionAction<PerformancePolicy, Object> {
    @Override
    public void transitionTo(PerformancePolicy policy, Object payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Implementation for delete if needed
    }
}
