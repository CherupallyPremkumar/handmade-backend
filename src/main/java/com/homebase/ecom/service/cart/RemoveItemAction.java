package com.homebase.ecom.service.cart;

import com.homebase.ecom.domain.Cart;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class RemoveItemAction implements STMTransitionAction<Cart> {

    @Override
    public void doTransition(Cart stateEntity, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Implement remove item from cart logic
    }
}
