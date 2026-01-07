package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.ListingPolicy;
import com.handmade.ecommerce.policy.domain.entity.ListingPolicyRule;
import com.handmade.ecommerce.policy.dto.AddListingRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class AddListingRuleAction extends AbstractSTMTransitionAction<ListingPolicy, AddListingRulePayload> {
    @Override
    public void transitionTo(ListingPolicy policy, AddListingRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        ListingPolicyRule rule = new ListingPolicyRule();
        rule.setRuleName(payload.getRuleName());
        rule.setViolationType(payload.getViolationType());
        rule.setActionRequired(payload.getActionRequired());
        rule.setRequired(payload.getRequired());
        rule.setThresholdValue(payload.getThresholdValue());
        policy.addRule(rule);
    }
}
