package com.handmade.ecommerce.subscription.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.customer.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.subscription.configuration.dao.SubscriptionRepository;
import java.util.Optional;

public class SubscriptionEntityStore implements EntityStore<Subscription>{
    @Autowired private SubscriptionRepository subscriptionRepository;

	@Override
	public void store(Subscription entity) {
        subscriptionRepository.save(entity);
	}

	@Override
	public Subscription retrieve(String id) {
        Optional<Subscription> entity = subscriptionRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Subscription with ID " + id);
	}

}
