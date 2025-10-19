package com.homebase.ecom.service.customer;

import com.homebase.ecom.domain.Customer;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class PromoteToVipAction implements STMTransitionAction<Customer> {

    @Override
    public void doTransition(Customer customer, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Promote customer to VIP status
        // Grant VIP privileges
        // Send VIP welcome email
        System.out.println("Customer promoted to VIP: " + customer.getEmail());
    }
}
