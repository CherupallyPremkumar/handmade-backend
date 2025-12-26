package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.dto.command.RejectSellerPayload;

import java.time.LocalDateTime;

public class RejectSellerAction extends AbstractSTMTransitionAction<Seller, RejectSellerPayload> {

    @Override
    public void transitionTo(Seller seller, RejectSellerPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Store rejection reason
        if (payload.getRejectionReason() != null) {
            // TODO: Store in seller notes or separate rejection table
            seller.setUpdatedAt(LocalDateTime.now());
        }
        
        // TODO: Send rejection email with reason and reapplication instructions
        // emailService.sendRejectionEmail(seller, payload.getRejectionReason());
        
        // TODO: Notify admin team
        // notificationService.notifyAdminSellerRejected(seller);
    }
}
