package com.handmade.ecommerce.pricerule.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.offer.model.PriceRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.pricerule.configuration.dao.PriceRuleRepository;
import java.util.Optional;

public class PriceRuleEntityStore implements EntityStore<PriceRule>{
    @Autowired private PriceRuleRepository priceruleRepository;

	@Override
	public void store(PriceRule entity) {
        priceruleRepository.save(entity);
	}

	@Override
	public PriceRule retrieve(String id) {
        Optional<PriceRule> entity = priceruleRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PriceRule with ID " + id);
	}

}
