package com.handmade.ecommerce.sellerstore.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.seller.model.SellerStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.sellerstore.configuration.dao.SellerStoreRepository;
import java.util.Optional;

public class SellerStoreEntityStore implements EntityStore<SellerStore>{
    @Autowired private SellerStoreRepository sellerstoreRepository;

	@Override
	public void store(SellerStore entity) {
        sellerstoreRepository.save(entity);
	}

	@Override
	public SellerStore retrieve(String id) {
        Optional<SellerStore> entity = sellerstoreRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find SellerStore with ID " + id);
	}

}
