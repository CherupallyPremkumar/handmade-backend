package com.handmade.ecommerce.offer.service.cmds;

import com.handmade.ecommerce.offer.domain.aggregate.Offer;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.activities.AreActivitiesComplete;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Buy Box Gate Reconciliation Logic.
 * Checks if both Inventory and Compliance signals have been recorded 
 * as successful activities. If so, triggers the final 'activate' event.
 */
public class ReconcileOfferActivationAction implements STMTransitionAction<Offer> {

    @Autowired
    private AreActivitiesComplete areActivitiesComplete;

    @Override
    public void doTransition(Offer offer, Object param, State startState, String eventId, 
                             State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // This action is called when a signal (INVENTORY_READY or COMPLIANCE_READY) is received.
        // We check if all required activities for the current state (PENDING_ACTIVATION) are complete.
        
        // Required activities are defined in the STM XML or implicitly by names.
        // For this reconciliation, we explicitly check for "INVENTORY_READY" and "COMPLIANCE_READY"
        
        boolean inventoryReady = isActivityComplete(offer, "INVENTORY_READY");
        boolean complianceReady = isActivityComplete(offer, "COMPLIANCE_READY");
        
        if (inventoryReady && complianceReady) {
            // All signals present. Open the Buy Box Gate!
            stm.processById(offer.getId(), "activate", null);
        }
    }

    private boolean isActivityComplete(Offer offer, String activityName) {
        return offer.obtainActivities().stream()
                .anyMatch(a -> a.getName().equals(activityName) && a.getSuccess());
    }
}
