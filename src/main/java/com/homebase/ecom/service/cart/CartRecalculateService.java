package com.homebase.ecom.service.cart;

import com.homebase.ecom.domain.Cart;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMAutomaticStateComputation;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class CartRecalculateService implements STMAutomaticStateComputation<Cart> {

    @Override
    public String execute(Cart stateEntity) {
        return "";
    }
}
