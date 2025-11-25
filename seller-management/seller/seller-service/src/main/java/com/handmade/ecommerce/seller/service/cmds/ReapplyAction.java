package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.model.Seller;
import org.chenile.workflow.param.MinimalPayload;

import java.time.LocalDateTime;

public class ReapplyAction extends AbstractSTMTransitionAction<Seller, MinimalPayload> {

    @Override
    public void transitionTo(Seller seller, MinimalPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Reset seller for reapplication
        seller.setUpdatedAt(LocalDateTime.now());
        
        // TODO: Clear previous rejection data but keep seller history
        // Clear activities from previous application
        
        // TODO: Notify seller that they can update their application
        // notificationService.notifyReapplicationStarted(seller);
    }
}
