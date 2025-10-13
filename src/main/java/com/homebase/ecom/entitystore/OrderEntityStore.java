package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Order;
import com.homebase.ecom.repository.OrderRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class OrderEntityStore implements EntityStore<Order> {
    

    private final OrderRepository orderRepository;

    public OrderEntityStore(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void store(Order entity) {

    }

    @Override
    public Order retrieve(String id) {
        return null;
    }
}
