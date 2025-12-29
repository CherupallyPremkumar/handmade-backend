package com.handmade.ecommerce.product.service.cmds;

import com.handmade.ecommerce.product.dto.OnSubmitProductPayload;
import com.handmade.ecommerce.product.domain.model.Product;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.StateEntity;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public class OnSubmitProductService extends AbstractSTMTransitionAction<Product, OnSubmitProductPayload> {

    @Override
    public void transitionTo(Product stateEntity, OnSubmitProductPayload transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

    }
}
