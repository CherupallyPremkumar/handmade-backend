package com.handmade.ecommerce.seller.account.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.account.dto.RequestMoreInfoPayload;

import java.time.LocalDateTime;

public class RequestMoreInfoAction extends AbstractSTMTransitionAction<SellerAccount, RequestMoreInfoPayload> {

    @Override
    public void transitionTo(SellerAccount seller, RequestMoreInfoPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Store the information request details
        if (payload.getInfoRequested() != null) {
            seller.setUpdatedAt(LocalDateTime.now());
        }
    }
}
