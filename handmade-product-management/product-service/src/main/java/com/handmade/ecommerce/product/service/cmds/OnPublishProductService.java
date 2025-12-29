package com.handmade.ecommerce.product.service.cmds;

import com.handmade.ecommerce.product.domain.model.Product;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Component;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.product.dto.PublishProductPayload;

@Component
public class OnPublishProductService extends AbstractSTMTransitionAction<Product, PublishProductPayload> {

    @Override
    public void transitionTo(Product product, PublishProductPayload transitionParam, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Publish product logic
        product.setPublished(true);
    }
}
