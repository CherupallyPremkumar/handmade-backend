package com.homebase.ecom.service.customer;

import com.homebase.ecom.domain.Customer;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.stereotype.Service;

@Service
public class PlaceOrderAction implements STMTransitionAction<Customer> {

    @Override
    public void doTransition(Customer customer, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        // Process order placement
        // Update order count
        // Update total spent
        customer.setOrderCount(customer.getOrderCount() + 1);
        System.out.println("Order placed by customer: " + customer.getEmail());
    }
}
