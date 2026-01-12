package com.handmade.ecommerce.seller.onboarding.store;

import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import com.handmade.ecommerce.seller.onboarding.repository.SellerOnboardingCaseRepository;
import com.handmade.ecommerce.seller.onboarding.repository.SellerOnboardingStepRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SellerOnboardingStoreImpl implements EntityStore<SellerOnboardingCase> {

    @Autowired
    private SellerOnboardingCaseRepository repository;

    @Autowired
    private SellerOnboardingStepRepository sellerOnboardingStepRepository;

    @Override
    public void store(SellerOnboardingCase entity) {
        repository.save(entity);
    }

    @Override
    public SellerOnboardingCase retrieve(String id) {
        return repository.findById(id).orElse(null);
    }
}
