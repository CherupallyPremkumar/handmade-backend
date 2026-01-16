package com.handmade.ecommerce.logistics.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.logistics.model.Logistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.logistics.configuration.dao.LogisticsRepository;
import java.util.Optional;

public class LogisticsEntityStore implements EntityStore<Logistics>{
    @Autowired private LogisticsRepository logisticsRepository;

	@Override
	public void store(Logistics entity) {
        logisticsRepository.save(entity);
	}

	@Override
	public Logistics retrieve(String id) {
        Optional<Logistics> entity = logisticsRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Logistics with ID " + id);
	}

}
