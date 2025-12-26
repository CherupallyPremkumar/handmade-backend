package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.dto.command.RequestMoreInfoPayload;

import java.time.LocalDateTime;

public class RequestMoreInfoAction extends AbstractSTMTransitionAction<Seller, RequestMoreInfoPayload> {

    @Override
    public void transitionTo(Seller seller, RequestMoreInfoPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Store the information request details
        if (payload.getInfoRequested() != null) {
            // In a real implementation, you might store this in a separate table
            // For now, we can add it to seller's notes or a custom field
            seller.setUpdatedAt(LocalDateTime.now());
        }
        
        // TODO: Send notification to seller about information request
        // notificationService.sendInfoRequest(seller, payload.getInfoRequested());
    }
}
