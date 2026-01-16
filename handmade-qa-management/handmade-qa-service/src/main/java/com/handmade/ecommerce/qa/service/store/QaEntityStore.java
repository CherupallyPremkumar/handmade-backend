package com.handmade.ecommerce.qa.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.qa.model.Qa;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.qa.configuration.dao.QaRepository;
import java.util.Optional;

public class QaEntityStore implements EntityStore<Qa>{
    @Autowired private QaRepository qaRepository;

	@Override
	public void store(Qa entity) {
        qaRepository.save(entity);
	}

	@Override
	public Qa retrieve(String id) {
        Optional<Qa> entity = qaRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Qa with ID " + id);
	}

}
