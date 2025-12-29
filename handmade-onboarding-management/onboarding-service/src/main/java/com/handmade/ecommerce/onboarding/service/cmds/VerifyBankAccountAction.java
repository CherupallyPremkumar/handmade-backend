package com.handmade.ecommerce.onboarding.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.dto.VerifyBankAccountPayload;

import java.time.LocalDateTime;

public class VerifyBankAccountAction extends AbstractSTMTransitionAction<SellerOnboardingCase, VerifyBankAccountPayload> {

    @Override
    public void transitionTo(SellerOnboardingCase seller, VerifyBankAccountPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Validate bank account verification and store tokenization data
        if (payload.isVerified()) {
            // Bank account verification successful
            seller.setExternalPayoutToken(payload.getPayoutToken());
            seller.setExternalPayoutProvider(payload.getProvider());
            seller.bankDetailsProvided = true;
            seller.setUpdatedAt(LocalDateTime.now());
        }
    }
}
