package com.homebase.ecom.service.cartitem;

import com.homebase.ecom.command.cartitem.CartItemIncrementQtyPayLoad;
import com.homebase.ecom.domain.CartItem;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class CartItemIncrementQtyService implements STMTransitionAction<CartItem> {
    @Override
    public void doTransition(CartItem cartItem, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        CartItemIncrementQtyPayLoad payload = (CartItemIncrementQtyPayLoad) transitionParam;
        cartItem.incrementQuantity(payload.getQuantity());
    }
}
