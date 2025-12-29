package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.VerifyKYCPayload;

public class VerifyKYCAction extends AbstractSTMTransitionAction<SellerAccount, VerifyKYCPayload> {

    @Override
    public void doTransition(SellerAccount seller, VerifyKYCPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Validate KYC verification
        if (payload.isVerified()) {
            // KYC verification successful
            seller.setUpdatedAt(java.time.LocalDateTime.now());
            
            // TODO: Store KYC verification details
            // verificationService.storeKYCVerification(seller, payload.getComments());
        } else {
            // KYC verification failed
            // TODO: Request additional KYC documents
            // notificationService.requestAdditionalKYCDocuments(seller, payload.getComments());
        }
        
        // Activity completion is handled by STM framework
    }
}
