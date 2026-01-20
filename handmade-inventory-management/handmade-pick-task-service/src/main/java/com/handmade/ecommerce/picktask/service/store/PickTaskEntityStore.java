package com.handmade.ecommerce.picktask.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.inventory.model.PickTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.picktask.configuration.dao.PickTaskRepository;
import java.util.Optional;

public class PickTaskEntityStore implements EntityStore<PickTask>{
    @Autowired private PickTaskRepository picktaskRepository;

	@Override
	public void store(PickTask entity) {
        picktaskRepository.save(entity);
	}

	@Override
	public PickTask retrieve(String id) {
        Optional<PickTask> entity = picktaskRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PickTask with ID " + id);
	}

}
