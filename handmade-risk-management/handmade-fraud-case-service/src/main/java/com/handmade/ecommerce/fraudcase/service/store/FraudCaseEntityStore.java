package com.handmade.ecommerce.fraudcase.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.risk.model.FraudCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.fraudcase.configuration.dao.FraudCaseRepository;
import java.util.Optional;

public class FraudCaseEntityStore implements EntityStore<FraudCase>{
    @Autowired private FraudCaseRepository fraudcaseRepository;

	@Override
	public void store(FraudCase entity) {
        fraudcaseRepository.save(entity);
	}

	@Override
	public FraudCase retrieve(String id) {
        Optional<FraudCase> entity = fraudcaseRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find FraudCase with ID " + id);
	}

}
