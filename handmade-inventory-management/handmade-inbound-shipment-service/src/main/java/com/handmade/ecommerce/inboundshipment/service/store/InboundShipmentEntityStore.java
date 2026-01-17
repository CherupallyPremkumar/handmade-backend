package com.handmade.ecommerce.inboundshipment.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.inventory.model.InboundShipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.inboundshipment.configuration.dao.InboundShipmentRepository;
import java.util.Optional;

public class InboundShipmentEntityStore implements EntityStore<InboundShipment>{
    @Autowired private InboundShipmentRepository inboundshipmentRepository;

	@Override
	public void store(InboundShipment entity) {
        inboundshipmentRepository.save(entity);
	}

	@Override
	public InboundShipment retrieve(String id) {
        Optional<InboundShipment> entity = inboundshipmentRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find InboundShipment with ID " + id);
	}

}
