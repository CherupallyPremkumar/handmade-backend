package com.homebase.ecom.service.order;

import com.homebase.ecom.domain.Order;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class ReturnOrderInShippedService implements STMTransitionAction<Order> {

    @Override
    public void doTransition(Order stateEntity, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Implement return order from shipped state logic
    }
}
