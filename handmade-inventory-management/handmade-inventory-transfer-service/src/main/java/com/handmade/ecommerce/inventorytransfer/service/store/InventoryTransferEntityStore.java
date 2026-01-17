package com.handmade.ecommerce.inventorytransfer.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.inventory.model.InventoryTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.inventorytransfer.configuration.dao.InventoryTransferRepository;
import java.util.Optional;

public class InventoryTransferEntityStore implements EntityStore<InventoryTransfer>{
    @Autowired private InventoryTransferRepository inventorytransferRepository;

	@Override
	public void store(InventoryTransfer entity) {
        inventorytransferRepository.save(entity);
	}

	@Override
	public InventoryTransfer retrieve(String id) {
        Optional<InventoryTransfer> entity = inventorytransferRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find InventoryTransfer with ID " + id);
	}

}
