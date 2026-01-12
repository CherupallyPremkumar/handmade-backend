package com.handmade.ecommerce.seller.onboarding.port;

import java.util.List;

import com.handmade.ecommerce.seller.onboarding.dto.OnboardingResponse;
import com.handmade.ecommerce.seller.onboarding.dto.OnboardingStepResponse;

/**
 * Seller Onboarding Service
 * API Interface using DTOs to maintain module boundaries.
 */
public interface SellerOnboardingService {
    OnboardingResponse getOnboarding(String caseId);

    List<OnboardingStepResponse> getSteps(String caseId);

    void handleStripeWebHook(Object payload);
}