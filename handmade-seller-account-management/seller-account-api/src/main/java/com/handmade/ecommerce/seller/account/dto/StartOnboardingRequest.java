package com.handmade.ecommerce.seller.account.dto;

/**
 * Public request DTO for starting the seller onboarding process.
 */
public record StartOnboardingRequest(
        String email,
        String country,
        String sellerType) {
}
