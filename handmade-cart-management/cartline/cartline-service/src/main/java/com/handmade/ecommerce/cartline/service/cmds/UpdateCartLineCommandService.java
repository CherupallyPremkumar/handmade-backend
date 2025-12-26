package com.handmade.ecommerce.cartline.service.cmds;

import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.command.cart.UpdateCartLinePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public class UpdateCartLineCommandService extends AbstractSTMTransitionAction<Cartline, UpdateCartLinePayload> {
    @Override
    public void transitionTo(Cartline stateEntity, UpdateCartLinePayload transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

    }
}
