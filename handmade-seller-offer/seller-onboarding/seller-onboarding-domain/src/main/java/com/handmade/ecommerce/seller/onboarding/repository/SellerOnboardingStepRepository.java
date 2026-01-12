package com.handmade.ecommerce.seller.onboarding.repository;

import com.handmade.ecommerce.seller.onboarding.model.SellerOnboardingStep;
import java.util.List;

/**
 * Pure Domain Repository interface for SellerOnboardingStep
 */
public interface SellerOnboardingStepRepository {
    List<SellerOnboardingStep> findByOnboardingCaseId(String onboardingCaseId);

    List<SellerOnboardingStep> findByStepName(String stepName);

    List<SellerOnboardingStep> findByStatus(String status);
}
