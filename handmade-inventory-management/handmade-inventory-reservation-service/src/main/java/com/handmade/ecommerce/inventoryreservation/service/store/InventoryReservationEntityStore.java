package com.handmade.ecommerce.inventoryreservation.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.inventory.model.InventoryReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.inventoryreservation.configuration.dao.InventoryReservationRepository;
import java.util.Optional;

public class InventoryReservationEntityStore implements EntityStore<InventoryReservation>{
    @Autowired private InventoryReservationRepository inventoryreservationRepository;

	@Override
	public void store(InventoryReservation entity) {
        inventoryreservationRepository.save(entity);
	}

	@Override
	public InventoryReservation retrieve(String id) {
        Optional<InventoryReservation> entity = inventoryreservationRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find InventoryReservation with ID " + id);
	}

}
