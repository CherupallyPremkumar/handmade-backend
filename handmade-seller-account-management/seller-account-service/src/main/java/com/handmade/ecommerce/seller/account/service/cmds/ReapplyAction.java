package com.handmade.ecommerce.seller.account.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.account.dto.ReapplyPayload;

import java.time.LocalDateTime;

public class ReapplyAction extends AbstractSTMTransitionAction<SellerAccount, ReapplyPayload> {

    @Override
    public void transitionTo(SellerAccount seller, ReapplyPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Reset seller for reapplication
        seller.setUpdatedAt(LocalDateTime.now());
    }
}
