package com.handmade.ecommerce.cartline.service.cmds;

import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.command.cart.CloseCartLinePayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.springframework.stereotype.Component;

@Component
public class CloseCartLineCommandService  extends AbstractSTMTransitionAction<Cartline, CloseCartLinePayload> {
    @Override
    public void transitionTo(Cartline stateEntity, CloseCartLinePayload transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

    }
}
