package com.handmade.ecommerce.onboarding.service.store;

import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import org.chenile.utils.entity.service.EntityStore;

public interface SellerOnboardingCaseStore<T> extends EntityStore<SellerOnboardingCase> {

    boolean existsByEmail(String email);
}