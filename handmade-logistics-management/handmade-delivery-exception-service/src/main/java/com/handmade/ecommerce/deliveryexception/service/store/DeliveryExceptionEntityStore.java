package com.handmade.ecommerce.deliveryexception.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.logistics.model.DeliveryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.deliveryexception.configuration.dao.DeliveryExceptionRepository;
import java.util.Optional;

public class DeliveryExceptionEntityStore implements EntityStore<DeliveryException>{
    @Autowired private DeliveryExceptionRepository deliveryexceptionRepository;

	@Override
	public void store(DeliveryException entity) {
        deliveryexceptionRepository.save(entity);
	}

	@Override
	public DeliveryException retrieve(String id) {
        Optional<DeliveryException> entity = deliveryexceptionRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find DeliveryException with ID " + id);
	}

}
