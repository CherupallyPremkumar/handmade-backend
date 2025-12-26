package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.dto.command.VerifyBankAccountPayload;

public class VerifyBankAccountAction extends AbstractSTMTransitionAction<Seller, VerifyBankAccountPayload> {

    @Override
    public void transitionTo(Seller seller, VerifyBankAccountPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Validate bank account verification
        if (payload.isVerified()) {
            // Bank account verification successful
            seller.setUpdatedAt(java.time.LocalDateTime.now());
            
            // TODO: Store bank verification details
            // verificationService.storeBankVerification(seller, payload.getComments());
        } else {
            // Bank verification failed
            // TODO: Request corrected bank details
            // notificationService.requestBankDetailsCorrection(seller, payload.getComments());
        }
        
        // Activity completion is handled by STM framework
    }
}
