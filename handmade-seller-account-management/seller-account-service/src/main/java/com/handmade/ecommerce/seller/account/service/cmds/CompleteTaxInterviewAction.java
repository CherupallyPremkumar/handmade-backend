package com.handmade.ecommerce.seller.account.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.account.dto.CompleteTaxInterviewPayload;

import java.time.LocalDateTime;

public class CompleteTaxInterviewAction extends AbstractSTMTransitionAction<SellerAccount, CompleteTaxInterviewPayload> {

    @Override
    public void transitionTo(SellerAccount seller, CompleteTaxInterviewPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Update seller with tax info
        seller.getBusinessDetails().setTaxId(payload.getTaxId());
        seller.setUpdatedAt(LocalDateTime.now());
    }
}
