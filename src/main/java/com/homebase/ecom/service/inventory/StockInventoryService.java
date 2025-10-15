package com.homebase.ecom.service.inventory;

import com.homebase.ecom.domain.Inventory;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class StockInventoryService implements STMTransitionAction<Inventory> {

    @Override
    public void doTransition(Inventory stateEntity, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Implement stock inventory logic
    }
}
