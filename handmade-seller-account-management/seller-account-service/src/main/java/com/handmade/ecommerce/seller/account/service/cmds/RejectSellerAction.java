package com.handmade.ecommerce.seller.account.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.account.dto.RejectSellerPayload;

import java.time.LocalDateTime;

public class RejectSellerAction extends AbstractSTMTransitionAction<SellerAccount, RejectSellerPayload> {


    @Override
    public void transitionTo(SellerAccount seller, RejectSellerPayload payload, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

        // Store rejection reason
        if (payload.getRejectionReason() != null) {
            seller.setUpdatedAt(LocalDateTime.now());
        }
    }
}
