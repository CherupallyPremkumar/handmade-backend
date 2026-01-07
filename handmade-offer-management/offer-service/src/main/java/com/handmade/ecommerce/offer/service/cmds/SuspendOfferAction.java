package com.handmade.ecommerce.offer.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.offer.domain.aggregate.Offer;
import com.handmade.ecommerce.offer.dto.OfferTransitionPayload;
import org.springframework.stereotype.Component;

@Component
public class SuspendOfferAction extends AbstractSTMTransitionAction<Offer, OfferTransitionPayload> {

    @Override
    public void transitionTo(Offer offer, OfferTransitionPayload payload,
                            State startState, String eventId,
                            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        if (payload != null && payload.getReason() != null) {
            offer.setSuspensionReason(payload.getReason());
        }
    }
}
