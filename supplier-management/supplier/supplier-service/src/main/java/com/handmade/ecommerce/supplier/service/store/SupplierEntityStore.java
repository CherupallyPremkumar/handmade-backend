package com.handmade.ecommerce.supplier.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.supplier.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.supplier.configuration.dao.SupplierRepository;
import java.util.Optional;

public class SupplierEntityStore implements EntityStore<Supplier>{
    @Autowired private SupplierRepository supplierRepository;

	@Override
	public void store(Supplier entity) {
        supplierRepository.save(entity);
	}

	@Override
	public Supplier retrieve(String id) {
        Optional<Supplier> entity = supplierRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Supplier with ID " + id);
	}

}
