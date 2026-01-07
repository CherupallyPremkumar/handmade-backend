package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.InventoryPolicy;
import com.handmade.ecommerce.policy.domain.entity.InventoryPolicyRule;
import com.handmade.ecommerce.policy.dto.UpdateInventoryRulePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class UpdateInventoryRuleAction extends AbstractSTMTransitionAction<InventoryPolicy, UpdateInventoryRulePayload> {
    @Override
    public void transitionTo(InventoryPolicy policy, UpdateInventoryRulePayload payload, State startState, 
                             String eventId, State endState, 
                             STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        InventoryPolicyRule rule = new InventoryPolicyRule();
        rule.setRuleName(payload.getRuleName());
        rule.setViolationType(payload.getViolationType());
        rule.setThresholdValue(payload.getThresholdValue());
        rule.setRequired(payload.getRequired());
        policy.updateRule(rule);
    }
}
