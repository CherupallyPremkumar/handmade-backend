package com.handmade.ecommerce.price.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.price.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.price.configuration.dao.PriceRepository;
import java.util.Optional;

public class PriceEntityStore implements EntityStore<Price>{
    @Autowired private PriceRepository priceRepository;

	@Override
	public void store(Price entity) {
        priceRepository.save(entity);
	}

	@Override
	public Price retrieve(String id) {
        Optional<Price> entity = priceRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Price with ID " + id);
	}

}
