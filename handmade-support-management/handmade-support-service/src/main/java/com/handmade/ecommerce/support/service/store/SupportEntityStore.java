package com.handmade.ecommerce.support.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.support.model.Support;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.support.configuration.dao.SupportRepository;
import java.util.Optional;

public class SupportEntityStore implements EntityStore<Support>{
    @Autowired private SupportRepository supportRepository;

	@Override
	public void store(Support entity) {
        supportRepository.save(entity);
	}

	@Override
	public Support retrieve(String id) {
        Optional<Support> entity = supportRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Support with ID " + id);
	}

}
