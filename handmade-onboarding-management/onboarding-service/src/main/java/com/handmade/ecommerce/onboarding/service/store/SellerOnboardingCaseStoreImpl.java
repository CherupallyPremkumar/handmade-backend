package com.handmade.ecommerce.onboarding.service.store;

import com.handmade.ecommerce.onboarding.infrastructure.persistence.SellerOnboardingCaseRepository;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerOnboardingCaseStoreImpl implements SellerOnboardingCaseStore<SellerOnboardingCase> {
    @Autowired
    private SellerOnboardingCaseRepository repository;

    @Override
    public void store(SellerOnboardingCase entity) {
        repository.save(entity);
    }

    @Override
    public SellerOnboardingCase retrieve(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
