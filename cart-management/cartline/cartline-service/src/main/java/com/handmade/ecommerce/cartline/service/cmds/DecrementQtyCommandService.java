package com.handmade.ecommerce.cartline.service.cmds;

import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.command.cart.CartLineDecrementQtyPayLoad;
import org.chenile.owiz.OrchExecutor;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.springframework.beans.factory.annotation.Autowired;

public class DecrementQtyCommandService extends AbstractSTMTransitionAction<Cartline, CartLineDecrementQtyPayLoad> {

    @Autowired
    private OrchExecutor<Cartline> cartlineOrchExecutor;
    
    @Override
    public void transitionTo(Cartline stateEntity, CartLineDecrementQtyPayLoad payLoad, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        stateEntity.decrementQty();
        cartlineOrchExecutor.execute(stateEntity);
    }
}