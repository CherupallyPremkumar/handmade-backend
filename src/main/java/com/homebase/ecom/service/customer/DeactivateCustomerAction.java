package com.homebase.ecom.service.customer;

import com.homebase.ecom.domain.Customer;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class DeactivateCustomerAction implements STMTransitionAction<Customer> {

    @Override
    public void doTransition(Customer customer, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Deactivate customer account
        // Clear active sessions
        // Send confirmation email
        System.out.println("Customer deactivated: " + customer.getEmail());
    }
}
