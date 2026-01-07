package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.PricingPolicy;
import com.handmade.ecommerce.policy.dto.ApprovePricingPolicyPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class ApprovePricingPolicyAction extends AbstractSTMTransitionAction<PricingPolicy, ApprovePricingPolicyPayload> {

    @Override
    public void transitionTo(PricingPolicy pricingPolicy, ApprovePricingPolicyPayload payload, State startState, 
                             String eventId, State endState,  
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        if (payload != null) {
            pricingPolicy.approve(payload.getApprovedBy());
        } else {
            pricingPolicy.approve("SYSTEM");
        }
    }
}
