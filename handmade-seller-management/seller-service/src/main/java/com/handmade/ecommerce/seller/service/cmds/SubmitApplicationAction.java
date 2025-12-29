package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.SubmitApplicationPayload;

import java.time.LocalDateTime;

public class SubmitApplicationAction extends AbstractSTMTransitionAction<SellerAccount, SubmitApplicationPayload> {

    @Override
    public void doTransition(SellerAccount seller, SubmitApplicationPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Validate that seller has minimum required information
        validateSellerApplication(seller);
        
        // Set submission timestamp
        seller.setUpdatedAt(LocalDateTime.now());
        
        // TODO: Send notification to admin team for review
        // notificationService.notifyAdminNewApplication(seller);
        
        // TODO: Send confirmation email to seller
        // emailService.sendApplicationSubmittedEmail(seller);
    }
    
    private void validateSellerApplication(SellerAccount seller) {
        if (seller.getSellerName() == null || seller.getSellerName().isEmpty()) {
            throw new IllegalStateException("Seller name is required");
        }
        if (seller.getContactEmail() == null || seller.getContactEmail().isEmpty()) {
            throw new IllegalStateException("Contact email is required");
        }
        // Add more validation as needed
    }
}
