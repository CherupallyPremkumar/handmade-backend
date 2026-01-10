package com.handmade.ecommerce.seller.onboarding.service.commands;

public class OnboardingResumeContext extends OnboardingOrchContext<OnboardingResumeContext> {
    private String stripePayload;

    public OnboardingResumeContext(String onboardingCaseId, String stripePayload) {
        // In resume, we might only have caseId initially.
        // We'll resolve sellerId later if needed or pass it in.
        super(null, onboardingCaseId);
        this.stripePayload = stripePayload;
    }

    public String getStripePayload() {
        return stripePayload;
    }
}
