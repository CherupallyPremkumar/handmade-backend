package com.handmade.ecommerce.seller.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.model.Seller;
import org.chenile.workflow.param.MinimalPayload;

import java.time.LocalDateTime;

public class CancelTerminationAction extends AbstractSTMTransitionAction<Seller, MinimalPayload> {

    @Override
    public void transitionTo(Seller seller, MinimalPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Clear any termination-related data
        seller.setUpdatedAt(LocalDateTime.now());
        
        // TODO: Clear termination activities and reset seller to active operations
        // activityService.clearTerminationActivities(seller);
        
        // TODO: Notify seller that termination has been cancelled
        // notificationService.notifyTerminationCancelled(seller);
    }
}
