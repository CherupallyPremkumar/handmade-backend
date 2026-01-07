package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.PricingPolicy;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class DeprecatePricingPolicyAction extends AbstractSTMTransitionAction<PricingPolicy, Object> {
    @Override
    public void transitionTo(PricingPolicy pricingPolicy, Object payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        pricingPolicy.deprecate();
    }
}
