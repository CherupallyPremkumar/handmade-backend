package com.handmade.ecommerce.risk.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.risk.model.Risk;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.risk.configuration.dao.RiskRepository;
import java.util.Optional;

public class RiskEntityStore implements EntityStore<Risk>{
    @Autowired private RiskRepository riskRepository;

	@Override
	public void store(Risk entity) {
        riskRepository.save(entity);
	}

	@Override
	public Risk retrieve(String id) {
        Optional<Risk> entity = riskRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Risk with ID " + id);
	}

}
