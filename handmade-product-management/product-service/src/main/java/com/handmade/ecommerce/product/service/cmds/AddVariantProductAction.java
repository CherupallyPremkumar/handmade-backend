package com.handmade.ecommerce.product.service.cmds;

import com.handmade.ecommerce.product.domain.model.Product;
import com.handmade.ecommerce.product.dto.AddVariantProductPayload;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;

public class AddVariantProductAction extends AbstractSTMTransitionAction<Product, AddVariantProductPayload> {

    @Override
    public void transitionTo(Product product, AddVariantProductPayload payload, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Stub implementation for Phase 1
        // TODO: Implement logic to add variant linked to product
    }
}
