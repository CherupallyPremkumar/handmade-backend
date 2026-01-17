package com.handmade.ecommerce.selleronboardingcase.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.onboarding.model.SellerOnboardingCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.selleronboardingcase.configuration.dao.SellerOnboardingCaseRepository;
import java.util.Optional;

public class SellerOnboardingCaseEntityStore implements EntityStore<SellerOnboardingCase>{
    @Autowired private SellerOnboardingCaseRepository selleronboardingcaseRepository;

	@Override
	public void store(SellerOnboardingCase entity) {
        selleronboardingcaseRepository.save(entity);
	}

	@Override
	public SellerOnboardingCase retrieve(String id) {
        Optional<SellerOnboardingCase> entity = selleronboardingcaseRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find SellerOnboardingCase with ID " + id);
	}

}
