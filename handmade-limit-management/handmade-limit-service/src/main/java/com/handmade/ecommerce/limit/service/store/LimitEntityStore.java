package com.handmade.ecommerce.limit.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.limit.model.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.limit.configuration.dao.LimitRepository;
import java.util.Optional;

public class LimitEntityStore implements EntityStore<Limit>{
    @Autowired private LimitRepository limitRepository;

	@Override
	public void store(Limit entity) {
        limitRepository.save(entity);
	}

	@Override
	public Limit retrieve(String id) {
        Optional<Limit> entity = limitRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Limit with ID " + id);
	}

}
