package com.handmade.ecommerce.risksignal.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.risk.model.RiskSignal;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.risksignal.configuration.dao.RiskSignalRepository;
import java.util.Optional;

public class RiskSignalEntityStore implements EntityStore<RiskSignal>{
    @Autowired private RiskSignalRepository risksignalRepository;

	@Override
	public void store(RiskSignal entity) {
        risksignalRepository.save(entity);
	}

	@Override
	public RiskSignal retrieve(String id) {
        Optional<RiskSignal> entity = risksignalRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find RiskSignal with ID " + id);
	}

}
