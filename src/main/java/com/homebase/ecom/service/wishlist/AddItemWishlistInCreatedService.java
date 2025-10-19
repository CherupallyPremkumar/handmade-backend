package com.homebase.ecom.service.wishlist;

import com.homebase.ecom.domain.Wishlist;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class AddItemWishlistInCreatedService implements STMTransitionAction<Wishlist> {

    @Override
    public void doTransition(Wishlist stateEntity, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Implement add item to wishlist from created state logic
    }
}
