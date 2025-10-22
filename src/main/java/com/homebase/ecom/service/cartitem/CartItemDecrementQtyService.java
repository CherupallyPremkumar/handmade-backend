package com.homebase.ecom.service.cartitem;

import com.homebase.ecom.command.cartitem.CartItemDecrementQtyPayLoad;
import com.homebase.ecom.domain.CartItem;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartItemDecrementQtyService implements STMTransitionAction<CartItem> {

    @Override
    public void doTransition(CartItem cartItem, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        CartItemDecrementQtyPayLoad payload = (CartItemDecrementQtyPayLoad) transitionParam;
        cartItem.decrementQuantity(payload.getQuantity());
    }
}
