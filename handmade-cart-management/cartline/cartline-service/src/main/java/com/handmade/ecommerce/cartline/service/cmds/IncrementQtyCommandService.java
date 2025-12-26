package com.handmade.ecommerce.cartline.service.cmds;

import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.command.cart.CartLineIncrementQtyPayLoad;
import org.chenile.owiz.OrchExecutor;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.springframework.beans.factory.annotation.Autowired;

public class IncrementQtyCommandService extends AbstractSTMTransitionAction<Cartline, CartLineIncrementQtyPayLoad> {

    @Autowired
    private OrchExecutor<Cartline> cartlineOrchExecutor;

    @Override
    public void transitionTo(Cartline stateEntity, CartLineIncrementQtyPayLoad payLoad, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        stateEntity.incrementQty();
        cartlineOrchExecutor.execute(stateEntity);    
    }
}
