package com.handmade.ecommerce.product.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.product.domain.model.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.product.configuration.dao.VariantRepository;
import java.util.Optional;

public class VariantEntityStore implements EntityStore<Variant>{
    @Autowired private VariantRepository variantRepository;

	@Override
	public void store(Variant entity) {
        variantRepository.save(entity);
	}

	@Override
	public Variant retrieve(String id) {
        Optional<Variant> entity = variantRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Variant with ID " + id);
	}

}
