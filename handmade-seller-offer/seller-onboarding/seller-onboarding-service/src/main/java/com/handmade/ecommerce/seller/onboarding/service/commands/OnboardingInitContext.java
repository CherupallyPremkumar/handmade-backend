package com.handmade.ecommerce.seller.onboarding.service.commands;

public class OnboardingInitContext extends OnboardingOrchContext<OnboardingInitContext> {
    public OnboardingInitContext(String sellerId, String onboardingCaseId) {
        super(sellerId, onboardingCaseId);
    }
}
