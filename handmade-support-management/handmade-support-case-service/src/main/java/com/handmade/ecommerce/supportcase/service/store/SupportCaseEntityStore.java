package com.handmade.ecommerce.supportcase.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.support.model.SupportCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.supportcase.configuration.dao.SupportCaseRepository;
import java.util.Optional;

public class SupportCaseEntityStore implements EntityStore<SupportCase>{
    @Autowired private SupportCaseRepository supportcaseRepository;

	@Override
	public void store(SupportCase entity) {
        supportcaseRepository.save(entity);
	}

	@Override
	public SupportCase retrieve(String id) {
        Optional<SupportCase> entity = supportcaseRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find SupportCase with ID " + id);
	}

}
