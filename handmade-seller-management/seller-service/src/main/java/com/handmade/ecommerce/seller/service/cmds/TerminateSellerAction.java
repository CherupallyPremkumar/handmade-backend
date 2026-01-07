package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import com.handmade.ecommerce.seller.dto.command.TerminateSellerPayload;

public class TerminateSellerAction extends AbstractSTMTransitionAction<Seller, TerminateSellerPayload> {

    @Override
    public void transitionTo(Seller seller, TerminateSellerPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Store termination comments
        if (payload.getTerminationComments() != null) {
            // TODO: Store in seller history/audit log
            seller.setUpdatedAt(java.time.LocalDateTime.now());
        }
        
        // TODO: Archive seller data
        // archiveService.archiveSellerData(seller);
        
        // TODO: Disable seller access to dashboard
        // userService.disableSellerAccess(seller);
        
        // TODO: Send final termination confirmation email
        // emailService.sendTerminationConfirmation(seller);
    }
}
