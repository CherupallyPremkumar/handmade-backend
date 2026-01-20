package com.handmade.ecommerce.vendorpoline.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.inventory.model.VendorPOLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.vendorpoline.configuration.dao.VendorPOLineRepository;
import java.util.Optional;

public class VendorPOLineEntityStore implements EntityStore<VendorPOLine>{
    @Autowired private VendorPOLineRepository vendorpolineRepository;

	@Override
	public void store(VendorPOLine entity) {
        vendorpolineRepository.save(entity);
	}

	@Override
	public VendorPOLine retrieve(String id) {
        Optional<VendorPOLine> entity = vendorpolineRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find VendorPOLine with ID " + id);
	}

}
