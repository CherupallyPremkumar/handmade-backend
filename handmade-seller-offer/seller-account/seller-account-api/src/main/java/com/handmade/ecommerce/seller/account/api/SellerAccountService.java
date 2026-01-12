package com.handmade.ecommerce.seller.account.api;


import com.handmade.ecommerce.seller.onboarding.event.OnboardingApprovedEvent;

/**
 * Operational service for managing seller accounts after onboarding approval.
 */
public interface SellerAccountService {
    
    /**
     * Idempotently creates a seller account if it doesn't already exist.
     * @param event The onboarding approval event containing account details.
     */
    void createSellerAccountIfNotExists(OnboardingApprovedEvent event);
}
