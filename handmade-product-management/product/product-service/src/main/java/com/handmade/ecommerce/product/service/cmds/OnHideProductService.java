package com.handmade.ecommerce.product.service.cmds;

import com.handmade.ecommerce.event.EventBus;
import com.handmade.ecommerce.product.dto.OnHideProductPayload;
import com.handmade.ecommerce.product.model.Product;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class OnHideProductService implements STMTransitionAction<Product> {

    @Autowired
    EventBus eventBus;
    @Override
    public void doTransition(Product stateEntity, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        OnHideProductPayload payload = (OnHideProductPayload) transitionParam;
        eventBus.publish(payload);
    }
}
