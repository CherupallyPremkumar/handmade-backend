package com.handmade.ecommerce.onboarding.api;

import com.handmade.ecommerce.identity.domain.model.VerificationResult;

public interface OnBoardingInternalCaseService {
    void identityVerified(String onboardingCaseId, VerificationResult result);
}
