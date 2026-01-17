package com.handmade.ecommerce.returnitem.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.inventory.model.ReturnItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.returnitem.configuration.dao.ReturnItemRepository;
import java.util.Optional;

public class ReturnItemEntityStore implements EntityStore<ReturnItem>{
    @Autowired private ReturnItemRepository returnitemRepository;

	@Override
	public void store(ReturnItem entity) {
        returnitemRepository.save(entity);
	}

	@Override
	public ReturnItem retrieve(String id) {
        Optional<ReturnItem> entity = returnitemRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find ReturnItem with ID " + id);
	}

}
