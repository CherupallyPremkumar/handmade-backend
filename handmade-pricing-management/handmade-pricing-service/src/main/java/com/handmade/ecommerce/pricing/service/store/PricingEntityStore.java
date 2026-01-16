package com.handmade.ecommerce.pricing.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.pricing.model.Pricing;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.pricing.configuration.dao.PricingRepository;
import java.util.Optional;

public class PricingEntityStore implements EntityStore<Pricing>{
    @Autowired private PricingRepository pricingRepository;

	@Override
	public void store(Pricing entity) {
        pricingRepository.save(entity);
	}

	@Override
	public Pricing retrieve(String id) {
        Optional<Pricing> entity = pricingRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Pricing with ID " + id);
	}

}
