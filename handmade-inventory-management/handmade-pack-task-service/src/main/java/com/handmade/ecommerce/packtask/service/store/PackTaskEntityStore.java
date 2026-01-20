package com.handmade.ecommerce.packtask.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.inventory.model.PackTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.packtask.configuration.dao.PackTaskRepository;
import java.util.Optional;

public class PackTaskEntityStore implements EntityStore<PackTask>{
    @Autowired private PackTaskRepository packtaskRepository;

	@Override
	public void store(PackTask entity) {
        packtaskRepository.save(entity);
	}

	@Override
	public PackTask retrieve(String id) {
        Optional<PackTask> entity = packtaskRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PackTask with ID " + id);
	}

}
