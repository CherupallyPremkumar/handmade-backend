package com.handmade.ecommerce.seller.onboarding.service.commands;

public class OnboardingConfirmContext extends OnboardingOrchContext<OnboardingConfirmContext> {
    public OnboardingConfirmContext(String sellerId, String onboardingCaseId) {
        super(sellerId, onboardingCaseId);
    }
}
