package com.handmade.ecommerce.seller.account.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.account.dto.SubmitAdditionalInfoPayload;

import java.time.LocalDateTime;

public class SubmitAdditionalInfoAction extends AbstractSTMTransitionAction<SellerAccount, SubmitAdditionalInfoPayload> {

    @Override
    public void transitionTo(SellerAccount seller, SubmitAdditionalInfoPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Store the additional information provided
        if (payload.getAdditionalInfo() != null) {
            seller.setUpdatedAt(LocalDateTime.now());
        }
    }
}
