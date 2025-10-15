package com.homebase.ecom.service.category;

import com.homebase.ecom.domain.Category;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class ActivateCategoryInCreatedService implements STMTransitionAction<Category> {

    @Override
    public void doTransition(Category stateEntity, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Implement activate category logic
    }
}
