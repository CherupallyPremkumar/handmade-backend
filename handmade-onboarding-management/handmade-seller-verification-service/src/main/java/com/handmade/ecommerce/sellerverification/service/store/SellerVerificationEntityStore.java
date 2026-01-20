package com.handmade.ecommerce.sellerverification.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.onboarding.model.SellerVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.sellerverification.configuration.dao.SellerVerificationRepository;
import java.util.Optional;

public class SellerVerificationEntityStore implements EntityStore<SellerVerification>{
    @Autowired private SellerVerificationRepository sellerverificationRepository;

	@Override
	public void store(SellerVerification entity) {
        sellerverificationRepository.save(entity);
	}

	@Override
	public SellerVerification retrieve(String id) {
        Optional<SellerVerification> entity = sellerverificationRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find SellerVerification with ID " + id);
	}

}
