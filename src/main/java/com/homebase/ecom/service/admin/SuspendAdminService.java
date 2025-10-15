package com.homebase.ecom.service.admin;

import com.homebase.ecom.domain.Admin;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class SuspendAdminService implements STMTransitionAction<Admin> {

    @Override
    public void doTransition(Admin stateEntity, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Implement suspend admin logic
    }
}
