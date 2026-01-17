package com.handmade.ecommerce.limitcounter.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.limit.model.LimitCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.limitcounter.configuration.dao.LimitCounterRepository;
import java.util.Optional;

public class LimitCounterEntityStore implements EntityStore<LimitCounter>{
    @Autowired private LimitCounterRepository limitcounterRepository;

	@Override
	public void store(LimitCounter entity) {
        limitcounterRepository.save(entity);
	}

	@Override
	public LimitCounter retrieve(String id) {
        Optional<LimitCounter> entity = limitcounterRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find LimitCounter with ID " + id);
	}

}
