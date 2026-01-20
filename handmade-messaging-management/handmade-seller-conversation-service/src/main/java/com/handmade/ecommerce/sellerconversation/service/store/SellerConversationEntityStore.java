package com.handmade.ecommerce.sellerconversation.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.messaging.model.SellerConversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.sellerconversation.configuration.dao.SellerConversationRepository;
import java.util.Optional;

public class SellerConversationEntityStore implements EntityStore<SellerConversation>{
    @Autowired private SellerConversationRepository sellerconversationRepository;

	@Override
	public void store(SellerConversation entity) {
        sellerconversationRepository.save(entity);
	}

	@Override
	public SellerConversation retrieve(String id) {
        Optional<SellerConversation> entity = sellerconversationRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find SellerConversation with ID " + id);
	}

}
