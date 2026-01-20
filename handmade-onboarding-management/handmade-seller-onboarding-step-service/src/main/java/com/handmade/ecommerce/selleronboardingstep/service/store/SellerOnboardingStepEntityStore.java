package com.handmade.ecommerce.selleronboardingstep.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.onboarding.model.SellerOnboardingStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.selleronboardingstep.configuration.dao.SellerOnboardingStepRepository;
import java.util.Optional;

public class SellerOnboardingStepEntityStore implements EntityStore<SellerOnboardingStep>{
    @Autowired private SellerOnboardingStepRepository selleronboardingstepRepository;

	@Override
	public void store(SellerOnboardingStep entity) {
        selleronboardingstepRepository.save(entity);
	}

	@Override
	public SellerOnboardingStep retrieve(String id) {
        Optional<SellerOnboardingStep> entity = selleronboardingstepRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find SellerOnboardingStep with ID " + id);
	}

}
