package com.handmade.ecommerce.product.service.cmds;

import com.handmade.ecommerce.event.api.EventPublisher;
import com.handmade.ecommerce.product.dto.OnHideProductPayload;
import com.handmade.ecommerce.product.domain.model.Product;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

@Component
public class OnHideProductService extends AbstractSTMTransitionAction<Product, OnHideProductPayload> {

    @Autowired
    EventPublisher eventPublisher;

    @Override
    public void transitionTo(Product stateEntity, OnHideProductPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        eventPublisher.publish("product.hidden", payload);
    }
}
