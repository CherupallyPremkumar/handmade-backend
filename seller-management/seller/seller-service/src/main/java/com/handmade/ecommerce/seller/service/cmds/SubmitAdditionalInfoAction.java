package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.dto.command.SubmitAdditionalInfoPayload;

import java.time.LocalDateTime;

public class SubmitAdditionalInfoAction extends AbstractSTMTransitionAction<Seller, SubmitAdditionalInfoPayload> {

    @Override
    public void transitionTo(Seller seller, SubmitAdditionalInfoPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Store the additional information provided
        if (payload.getAdditionalInfo() != null) {
            seller.setUpdatedAt(LocalDateTime.now());
            // TODO: Store additional info in appropriate fields or related table
        }
        
        // TODO: Notify admin that additional info has been submitted
        // notificationService.notifyAdminInfoSubmitted(seller);
    }
}
