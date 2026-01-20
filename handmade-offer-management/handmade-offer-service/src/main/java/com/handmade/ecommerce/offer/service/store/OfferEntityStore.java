package com.handmade.ecommerce.offer.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.offer.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.offer.configuration.dao.OfferRepository;
import java.util.Optional;

public class OfferEntityStore implements EntityStore<Offer>{
    @Autowired private OfferRepository offerRepository;

	@Override
	public void store(Offer entity) {
        offerRepository.save(entity);
	}

	@Override
	public Offer retrieve(String id) {
        Optional<Offer> entity = offerRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Offer with ID " + id);
	}

}
