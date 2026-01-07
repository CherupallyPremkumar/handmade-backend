package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.PricingPolicy;
import com.handmade.ecommerce.policy.dto.RemovePricingRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class RemovePricingRuleAction extends AbstractSTMTransitionAction<PricingPolicy, RemovePricingRulePayload> {
    @Override
    public void transitionTo(PricingPolicy policy, RemovePricingRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        policy.removeRule(payload.getRuleName());
    }
}
