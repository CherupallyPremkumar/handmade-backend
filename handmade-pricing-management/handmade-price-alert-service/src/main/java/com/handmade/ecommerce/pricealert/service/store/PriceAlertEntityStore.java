package com.handmade.ecommerce.pricealert.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.pricing.model.PriceAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.pricealert.configuration.dao.PriceAlertRepository;
import java.util.Optional;

public class PriceAlertEntityStore implements EntityStore<PriceAlert>{
    @Autowired private PriceAlertRepository pricealertRepository;

	@Override
	public void store(PriceAlert entity) {
        pricealertRepository.save(entity);
	}

	@Override
	public PriceAlert retrieve(String id) {
        Optional<PriceAlert> entity = pricealertRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PriceAlert with ID " + id);
	}

}
