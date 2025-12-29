package com.handmade.ecommerce.product.service.cmds;

import com.handmade.ecommerce.product.domain.model.Product;
import org.chenile.workflow.param.MinimalPayload;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

public class DefaultProductSTMTransitionAction extends AbstractSTMTransitionAction<Product, MinimalPayload> {
    
    @Override
    public void transitionTo(Product product, MinimalPayload payload,
                 State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm,
                 Transition transition) {
        // Default no-op implementation
    }
}
