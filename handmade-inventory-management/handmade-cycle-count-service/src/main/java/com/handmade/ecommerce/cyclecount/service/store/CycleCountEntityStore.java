package com.handmade.ecommerce.cyclecount.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.inventory.model.CycleCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.cyclecount.configuration.dao.CycleCountRepository;
import java.util.Optional;

public class CycleCountEntityStore implements EntityStore<CycleCount>{
    @Autowired private CycleCountRepository cyclecountRepository;

	@Override
	public void store(CycleCount entity) {
        cyclecountRepository.save(entity);
	}

	@Override
	public CycleCount retrieve(String id) {
        Optional<CycleCount> entity = cyclecountRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find CycleCount with ID " + id);
	}

}
