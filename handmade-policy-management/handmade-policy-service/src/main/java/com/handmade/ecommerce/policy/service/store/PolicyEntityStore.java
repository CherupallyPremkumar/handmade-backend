package com.handmade.ecommerce.policy.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.policy.model.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.policy.configuration.dao.PolicyRepository;
import java.util.Optional;

public class PolicyEntityStore implements EntityStore<Policy>{
    @Autowired private PolicyRepository policyRepository;

	@Override
	public void store(Policy entity) {
        policyRepository.save(entity);
	}

	@Override
	public Policy retrieve(String id) {
        Optional<Policy> entity = policyRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Policy with ID " + id);
	}

}
