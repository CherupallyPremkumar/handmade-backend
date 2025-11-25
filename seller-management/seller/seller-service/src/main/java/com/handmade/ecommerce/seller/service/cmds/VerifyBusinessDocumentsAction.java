package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.dto.command.VerifyBusinessDocumentsPayload;

public class VerifyBusinessDocumentsAction extends AbstractSTMTransitionAction<Seller, VerifyBusinessDocumentsPayload> {

    @Override
    public void transitionTo(Seller seller, VerifyBusinessDocumentsPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Validate verification result
        if (payload.isVerified()) {
            // Document verification successful
            seller.setUpdatedAt(java.time.LocalDateTime.now());
            
            // TODO: Store verification details
            // verificationService.storeDocumentVerification(seller, payload.getComments());
        } else {
            // Document verification failed
            // TODO: Request additional documents or clarification
            // notificationService.requestAdditionalDocuments(seller, payload.getComments());
        }
        
        // Activity completion is handled by STM framework automatically
    }
}
