package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.dto.command.ApproveSellerPayload;

import java.time.LocalDateTime;

public class ApproveSellerAction extends AbstractSTMTransitionAction<Seller, ApproveSellerPayload> {

    @Override
    public void transitionTo(Seller seller, ApproveSellerPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Set approval timestamp
        seller.setUpdatedAt(LocalDateTime.now());
        
        // TODO: Create seller dashboard access
        // userService.createSellerDashboardAccess(seller);
        
        // TODO: Send welcome email with onboarding instructions
        // emailService.sendWelcomeEmail(seller);
        
        // TODO: Notify admin team of approval
        // notificationService.notifyAdminSellerApproved(seller);
    }
}
