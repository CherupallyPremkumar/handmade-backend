package com.handmade.ecommerce.deliveryattempt.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.logistics.model.DeliveryAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.deliveryattempt.configuration.dao.DeliveryAttemptRepository;
import java.util.Optional;

public class DeliveryAttemptEntityStore implements EntityStore<DeliveryAttempt>{
    @Autowired private DeliveryAttemptRepository deliveryattemptRepository;

	@Override
	public void store(DeliveryAttempt entity) {
        deliveryattemptRepository.save(entity);
	}

	@Override
	public DeliveryAttempt retrieve(String id) {
        Optional<DeliveryAttempt> entity = deliveryattemptRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find DeliveryAttempt with ID " + id);
	}

}
