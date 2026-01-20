package com.handmade.ecommerce.orderline.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.order.model.OrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.orderline.configuration.dao.OrderLineRepository;
import java.util.Optional;

public class OrderLineEntityStore implements EntityStore<OrderLine>{
    @Autowired private OrderLineRepository orderlineRepository;

	@Override
	public void store(OrderLine entity) {
        orderlineRepository.save(entity);
	}

	@Override
	public OrderLine retrieve(String id) {
        Optional<OrderLine> entity = orderlineRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find OrderLine with ID " + id);
	}

}
