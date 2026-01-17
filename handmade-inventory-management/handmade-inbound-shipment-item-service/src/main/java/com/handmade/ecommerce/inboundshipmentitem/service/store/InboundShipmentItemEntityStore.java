package com.handmade.ecommerce.inboundshipmentitem.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.inventory.model.InboundShipmentItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.inboundshipmentitem.configuration.dao.InboundShipmentItemRepository;
import java.util.Optional;

public class InboundShipmentItemEntityStore implements EntityStore<InboundShipmentItem>{
    @Autowired private InboundShipmentItemRepository inboundshipmentitemRepository;

	@Override
	public void store(InboundShipmentItem entity) {
        inboundshipmentitemRepository.save(entity);
	}

	@Override
	public InboundShipmentItem retrieve(String id) {
        Optional<InboundShipmentItem> entity = inboundshipmentitemRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find InboundShipmentItem with ID " + id);
	}

}
