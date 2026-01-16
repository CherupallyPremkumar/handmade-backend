package com.handmade.ecommerce.onboarding.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.onboarding.model.Onboarding;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.onboarding.configuration.dao.OnboardingRepository;
import java.util.Optional;

public class OnboardingEntityStore implements EntityStore<Onboarding>{
    @Autowired private OnboardingRepository onboardingRepository;

	@Override
	public void store(Onboarding entity) {
        onboardingRepository.save(entity);
	}

	@Override
	public Onboarding retrieve(String id) {
        Optional<Onboarding> entity = onboardingRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Onboarding with ID " + id);
	}

}
