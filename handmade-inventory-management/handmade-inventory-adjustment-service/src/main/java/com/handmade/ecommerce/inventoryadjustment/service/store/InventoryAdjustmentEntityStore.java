package com.handmade.ecommerce.inventoryadjustment.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.inventory.model.InventoryAdjustment;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.inventoryadjustment.configuration.dao.InventoryAdjustmentRepository;
import java.util.Optional;

public class InventoryAdjustmentEntityStore implements EntityStore<InventoryAdjustment>{
    @Autowired private InventoryAdjustmentRepository inventoryadjustmentRepository;

	@Override
	public void store(InventoryAdjustment entity) {
        inventoryadjustmentRepository.save(entity);
	}

	@Override
	public InventoryAdjustment retrieve(String id) {
        Optional<InventoryAdjustment> entity = inventoryadjustmentRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find InventoryAdjustment with ID " + id);
	}

}
