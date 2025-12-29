package com.handmade.ecommerce.onboarding.delegate;

import com.handmade.ecommerce.onboarding.dto.ActiveOnboardingCaseView;
import com.handmade.ecommerce.onboarding.dto.CreateSellerOnboardingCaseRequest;
import com.handmade.ecommerce.onboarding.dto.LockPolicyRequest;

import java.util.Optional;

/**
 * Client for Onboarding Management
 */
public interface OnboardingManagerClient {

    String createCase(CreateSellerOnboardingCaseRequest request);

    Optional<ActiveOnboardingCaseView> findActiveCase(String email, String country);

    void lockPolicy(String caseId, LockPolicyRequest lockPolicyRequest);

    boolean existsByEmail(String email);

}
