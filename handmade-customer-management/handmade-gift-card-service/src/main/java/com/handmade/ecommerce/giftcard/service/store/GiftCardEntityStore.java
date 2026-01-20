package com.handmade.ecommerce.giftcard.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.customer.model.GiftCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.giftcard.configuration.dao.GiftCardRepository;
import java.util.Optional;

public class GiftCardEntityStore implements EntityStore<GiftCard>{
    @Autowired private GiftCardRepository giftcardRepository;

	@Override
	public void store(GiftCard entity) {
        giftcardRepository.save(entity);
	}

	@Override
	public GiftCard retrieve(String id) {
        Optional<GiftCard> entity = giftcardRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find GiftCard with ID " + id);
	}

}
