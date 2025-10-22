package com.homebase.ecom.service.cartitem;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.service.PriceStateService;
import org.chenile.stm.action.STMTransitionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdatePriceCartItemService implements STMTransitionAction<CartItem> {

    @Autowired
    protected PriceStateService priceStateService;

    @Override
    public void doTransition(CartItem stateEntity, Object transitionParam, org.chenile.stm.State startState, String eventId, org.chenile.stm.State endState, org.chenile.stm.STMInternalTransitionInvoker<?> stm, org.chenile.stm.model.Transition transition) throws Exception {

    }
}
