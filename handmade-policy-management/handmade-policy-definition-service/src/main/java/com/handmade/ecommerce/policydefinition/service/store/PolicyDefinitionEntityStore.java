package com.handmade.ecommerce.policydefinition.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.policy.model.PolicyDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.policydefinition.configuration.dao.PolicyDefinitionRepository;
import java.util.Optional;

public class PolicyDefinitionEntityStore implements EntityStore<PolicyDefinition>{
    @Autowired private PolicyDefinitionRepository policydefinitionRepository;

	@Override
	public void store(PolicyDefinition entity) {
        policydefinitionRepository.save(entity);
	}

	@Override
	public PolicyDefinition retrieve(String id) {
        Optional<PolicyDefinition> entity = policydefinitionRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find PolicyDefinition with ID " + id);
	}

}
