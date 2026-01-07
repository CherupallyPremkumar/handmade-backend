package com.handmade.ecommerce.offer.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.offer.domain.aggregate.Offer;
import com.handmade.ecommerce.offer.domain.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import java.util.Optional;

public class OfferEntityStore implements EntityStore<Offer> {
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public void store(Offer entity) {
        offerRepository.save(entity);
    }

    @Override
    public Offer retrieve(String id) {
        Optional<Offer> entity = offerRepository.findById(id);
        if (entity.isPresent())
            return entity.get();
        throw new NotFoundException(1600, "Unable to find Offer with ID " + id);
    }

    public Offer retrieveByCode(String offerCode) {
        Optional<Offer> entity = offerRepository.findByOfferCode(offerCode);
        if (entity.isPresent())
            return entity.get();
        throw new NotFoundException(1601, "Unable to find Offer with code " + offerCode);
    }
}
