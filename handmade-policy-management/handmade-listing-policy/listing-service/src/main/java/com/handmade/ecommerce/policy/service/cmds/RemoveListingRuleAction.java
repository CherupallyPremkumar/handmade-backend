package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.ListingPolicy;
import com.handmade.ecommerce.policy.dto.RemoveListingRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class RemoveListingRuleAction extends AbstractSTMTransitionAction<ListingPolicy, RemoveListingRulePayload> {
    @Override
    public void transitionTo(ListingPolicy policy, RemoveListingRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        policy.removeRule(payload.getRuleName());
    }
}
