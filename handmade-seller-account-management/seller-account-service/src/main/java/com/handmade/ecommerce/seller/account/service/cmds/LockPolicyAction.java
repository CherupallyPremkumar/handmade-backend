package com.handmade.ecommerce.seller.account.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.account.dto.LockPolicyRequest;

import java.time.LocalDateTime;

/**
 * STM Action to lock policy version to an onboarding case.
 * Transition: DRAFT → POLICY_LOCKED
 * 
 * This is a critical step that ensures policy immutability.
 * Once locked, the case will follow the rules of this specific policy version,
 * even if the policy is updated later (grandfathering).
 */
public class LockPolicyAction extends AbstractSTMTransitionAction<SellerAccount, LockPolicyRequest> {

    @Override
    public void transitionTo(SellerAccount sellerCase, LockPolicyRequest payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Lock policy version (immutable)
        sellerCase.setOnboardingPolicyId(payload.getPolicyId());
        sellerCase.setOnboardingPolicyVersion(payload.getPolicyVersion());
        sellerCase.setPolicyLockedAt(LocalDateTime.now());
        
        sellerCase.setUpdatedAt(LocalDateTime.now());
    }
}
