package com.handmade.ecommerce.pricingruledefinition.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.pricing.model.PricingRuleDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.pricingruledefinition.configuration.dao.PricingRuleDefinitionRepository;
import java.util.Optional;

public class PricingRuleDefinitionEntityStore implements EntityStore<PricingRuleDefinition>{
    @Autowired private PricingRuleDefinitionRepository pricingruledefinitionRepository;

	@Override
	public void store(PricingRuleDefinition entity) {
        pricingruledefinitionRepository.save(entity);
	}

	@Override
	public PricingRuleDefinition retrieve(String id) {
        Optional<PricingRuleDefinition> entity = pricingruledefinitionRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PricingRuleDefinition with ID " + id);
	}

}
