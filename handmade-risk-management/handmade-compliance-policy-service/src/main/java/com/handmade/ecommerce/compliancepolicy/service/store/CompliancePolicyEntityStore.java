package com.handmade.ecommerce.compliancepolicy.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.risk.model.CompliancePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.compliancepolicy.configuration.dao.CompliancePolicyRepository;
import java.util.Optional;

public class CompliancePolicyEntityStore implements EntityStore<CompliancePolicy>{
    @Autowired private CompliancePolicyRepository compliancepolicyRepository;

	@Override
	public void store(CompliancePolicy entity) {
        compliancepolicyRepository.save(entity);
	}

	@Override
	public CompliancePolicy retrieve(String id) {
        Optional<CompliancePolicy> entity = compliancepolicyRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find CompliancePolicy with ID " + id);
	}

}
