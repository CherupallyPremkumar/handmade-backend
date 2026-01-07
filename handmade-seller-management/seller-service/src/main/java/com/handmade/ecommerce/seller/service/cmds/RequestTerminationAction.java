package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import com.handmade.ecommerce.seller.dto.command.RequestTerminationPayload;

public class RequestTerminationAction extends AbstractSTMTransitionAction<Seller, RequestTerminationPayload> {

    @Override
    public void transitionTo(Seller seller, RequestTerminationPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Store termination request details
        if (payload.getTerminationReason() != null) {
            // TODO: Store termination reason in seller notes or separate table
            seller.setUpdatedAt(java.time.LocalDateTime.now());
        }
        
        // TODO: Notify admin team of termination request
        // notificationService.notifyAdminTerminationRequested(seller);
        
        // TODO: Send email to seller confirming termination request
        // emailService.sendTerminationRequestConfirmation(seller);
    }
}
