package com.homebase.ecom.service.tenant;

import com.homebase.ecom.domain.Tenant;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class DeleteTenantInCreatedService implements STMTransitionAction<Tenant> {

    @Override
    public void doTransition(Tenant stateEntity, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Implement delete tenant from created state logic
    }
}
