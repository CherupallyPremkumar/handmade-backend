package com.handmade.ecommerce.seller.account.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.account.dto.ApproveSellerPayload;

import java.time.LocalDateTime;

public class ApproveSellerAction extends AbstractSTMTransitionAction<SellerAccount, ApproveSellerPayload> {

    @Override
    public void transitionTo(SellerAccount seller, ApproveSellerPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Set approval timestamp
        seller.setUpdatedAt(LocalDateTime.now());
    }
}
