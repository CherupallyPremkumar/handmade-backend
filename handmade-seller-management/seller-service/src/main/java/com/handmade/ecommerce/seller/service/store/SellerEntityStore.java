package com.handmade.ecommerce.seller.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.seller.infrastructure.persistence.SellerRepository;

import java.util.Optional;


public class SellerEntityStore implements EntityStore<Seller>{

	private final SellerRepository sellerRepository;

    public SellerEntityStore(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
	public void store(Seller entity) {
        sellerRepository.save(entity);
	}

	@Override
	public Seller retrieve(String id) {
        Optional<Seller> entity = sellerRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Seller with ID " + id);
	}

    public boolean existsByContactEmail(String email) {
        return sellerRepository.existsByContactEmail(email);
    }
}
