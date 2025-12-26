package com.handmade.ecommerce.orderline.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.orderline.model.Orderline;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.orderline.configuration.dao.OrderlineRepository;
import java.util.Optional;

public class OrderlineEntityStore implements EntityStore<Orderline>{
    @Autowired private OrderlineRepository orderlineRepository;

	@Override
	public void store(Orderline entity) {
        orderlineRepository.save(entity);
	}

	@Override
	public Orderline retrieve(String id) {
        Optional<Orderline> entity = orderlineRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Orderline with ID " + id);
	}

}
