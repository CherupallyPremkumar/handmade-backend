package com.homebase.ecom.service.productvariant;

import com.homebase.ecom.domain.ProductVariant;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class DiscontinueProductVariantService implements STMTransitionAction<ProductVariant> {

    @Override
    public void doTransition(ProductVariant stateEntity, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Implement discontinue product variant logic
    }
}
