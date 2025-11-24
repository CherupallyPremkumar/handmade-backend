package com.handmade.ecommerce.product.service.cmds;

import com.handmade.ecommerce.product.model.Product;
import org.chenile.stm.STM;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Component;


public class OnPublishProductService implements STMTransitionAction<Product> {
    @Override
    public void doTransition(Product stateEntity, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

    }
}
