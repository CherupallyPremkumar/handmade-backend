package com.handmade.ecommerce.product.service.cmds;

import com.handmade.ecommerce.product.domain.model.Product;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Component;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.chenile.workflow.param.MinimalPayload;

@Component
public class OnRequestChangesProductService extends AbstractSTMTransitionAction<Product, MinimalPayload> {
    @Override
    public void transitionTo(Product stateEntity, MinimalPayload transitionParam, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

    }
}
