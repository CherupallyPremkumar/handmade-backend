package com.handmade.ecommerce.sellerkyc.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.seller.model.SellerKyc;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.sellerkyc.configuration.dao.SellerKycRepository;
import java.util.Optional;

public class SellerKycEntityStore implements EntityStore<SellerKyc>{
    @Autowired private SellerKycRepository sellerkycRepository;

	@Override
	public void store(SellerKyc entity) {
        sellerkycRepository.save(entity);
	}

	@Override
	public SellerKyc retrieve(String id) {
        Optional<SellerKyc> entity = sellerkycRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find SellerKyc with ID " + id);
	}

}
