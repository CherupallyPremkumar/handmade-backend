package com.handmade.ecommerce.seller.account.api;

import com.handmade.ecommerce.seller.account.dto.StartOnboardingRequest;
import com.handmade.ecommerce.seller.account.dto.StartOnboardingResponse;

/**
 * Public interface for seller onboarding orchestration.
 */
public interface SellerOnboardingService {

    /**
     * Start the onboarding process for a new seller.
     * This is a synchronous orchestration that resolves policies and creates an
     * onboarding case.
     * 
     * @param request The onboarding request details
     * @return The initial orchestration response including case ID and next steps
     */
    StartOnboardingResponse startOnboarding(StartOnboardingRequest request);
}
