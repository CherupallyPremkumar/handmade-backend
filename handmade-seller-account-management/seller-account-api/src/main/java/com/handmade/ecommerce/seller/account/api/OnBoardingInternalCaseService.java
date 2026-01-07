package com.handmade.ecommerce.seller.account.api;

import com.handmade.ecommerce.identity.domain.model.VerificationResult;

public interface OnBoardingInternalCaseService {
    void identityVerified(String onboardingCaseId, VerificationResult result);
}
