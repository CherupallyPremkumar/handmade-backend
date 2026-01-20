package com.handmade.ecommerce.vendorpo.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.inventory.model.VendorPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.vendorpo.configuration.dao.VendorPORepository;
import java.util.Optional;

public class VendorPOEntityStore implements EntityStore<VendorPO>{
    @Autowired private VendorPORepository vendorpoRepository;

	@Override
	public void store(VendorPO entity) {
        vendorpoRepository.save(entity);
	}

	@Override
	public VendorPO retrieve(String id) {
        Optional<VendorPO> entity = vendorpoRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find VendorPO with ID " + id);
	}

}
