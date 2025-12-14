package com.handmade.ecommerce.cartline.service.cmds;

import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.command.cart.DeleteCartLinePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;


public class DeleteCartLineCommandService extends AbstractSTMTransitionAction<Cartline, DeleteCartLinePayload> {
    @Override
    public void transitionTo(Cartline stateEntity, DeleteCartLinePayload transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

    }
}
