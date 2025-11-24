package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.seller.dto.command.ApproveSellerPayload;
import com.handmade.ecommerce.seller.model.Seller;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class ApprovalSellerService extends AbstractSTMTransitionAction<Seller, ApproveSellerPayload> {
    @Override
    public void transitionTo(Seller stateEntity, ApproveSellerPayload transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

    }
}
