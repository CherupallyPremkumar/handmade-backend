package com.handmade.ecommerce.shipment.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.shipping.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.shipment.configuration.dao.ShipmentRepository;
import java.util.Optional;

public class ShipmentEntityStore implements EntityStore<Shipment>{
    @Autowired private ShipmentRepository shipmentRepository;

	@Override
	public void store(Shipment entity) {
        shipmentRepository.save(entity);
	}

	@Override
	public Shipment retrieve(String id) {
        Optional<Shipment> entity = shipmentRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Shipment with ID " + id);
	}

}
