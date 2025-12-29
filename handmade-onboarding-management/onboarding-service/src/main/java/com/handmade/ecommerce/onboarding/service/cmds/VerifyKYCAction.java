package com.handmade.ecommerce.onboarding.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.dto.VerifyKYCPayload;

import java.time.LocalDateTime;

public class VerifyKYCAction extends AbstractSTMTransitionAction<SellerOnboardingCase, VerifyKYCPayload> {

    @Override
    public void transitionTo(SellerOnboardingCase seller, VerifyKYCPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Validate KYC verification
        if (payload.isVerified()) {
            // KYC verification successful
            seller.setUpdatedAt(LocalDateTime.now());
        }
    }
}
