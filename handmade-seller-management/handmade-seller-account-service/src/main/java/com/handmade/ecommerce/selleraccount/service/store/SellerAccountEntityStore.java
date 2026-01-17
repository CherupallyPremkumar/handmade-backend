package com.handmade.ecommerce.selleraccount.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.seller.model.SellerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.selleraccount.configuration.dao.SellerAccountRepository;
import java.util.Optional;

public class SellerAccountEntityStore implements EntityStore<SellerAccount>{
    @Autowired private SellerAccountRepository selleraccountRepository;

	@Override
	public void store(SellerAccount entity) {
        selleraccountRepository.save(entity);
	}

	@Override
	public SellerAccount retrieve(String id) {
        Optional<SellerAccount> entity = selleraccountRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find SellerAccount with ID " + id);
	}

}
