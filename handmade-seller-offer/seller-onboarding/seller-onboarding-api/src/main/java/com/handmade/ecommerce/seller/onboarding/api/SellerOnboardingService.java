package com.handmade.ecommerce.seller.onboarding.api;

import com.handmade.ecommerce.seller.onboarding.api.dto.OnboardingResponse;
import com.handmade.ecommerce.seller.onboarding.api.dto.OnboardingStepResponse;

import java.util.List;

/**
 * Seller Onboarding Service
 * API Interface using DTOs to maintain module boundaries.
 */
public interface SellerOnboardingService {
    OnboardingResponse getOnboarding(String caseId);

    List<OnboardingStepResponse> getSteps(String caseId);

    void handleStripeWebHook(String payload);
}
