package com.homebase.ecom.service.price;

import com.homebase.ecom.domain.Price;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class ExpirePriceService implements STMTransitionAction<Price> {

    @Override
    public void doTransition(Price stateEntity, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Implement expire price logic
    }
}
