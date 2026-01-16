package com.handmade.ecommerce.promotion.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.promotion.model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.promotion.configuration.dao.PromotionRepository;
import java.util.Optional;

public class PromotionEntityStore implements EntityStore<Promotion>{
    @Autowired private PromotionRepository promotionRepository;

	@Override
	public void store(Promotion entity) {
        promotionRepository.save(entity);
	}

	@Override
	public Promotion retrieve(String id) {
        Optional<Promotion> entity = promotionRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Promotion with ID " + id);
	}

}
