package com.handmade.ecommerce.onboarding.api;

import com.handmade.ecommerce.identity.dto.IdentityVerificationView;
import com.handmade.ecommerce.onboarding.dto.ActiveOnboardingCaseView;
import com.handmade.ecommerce.onboarding.dto.CreateSellerOnboardingCaseRequest;
import com.handmade.ecommerce.onboarding.dto.LockPolicyRequest;
import java.util.Optional;

/**
 * Service interface for SellerOnboardingCase (Onboarding)
 */
public interface OnboardingCaseService {
        boolean existsByEmail(String email);

        String createCase(CreateSellerOnboardingCaseRequest request);

        Optional<ActiveOnboardingCaseView> findActiveCase(String email, String country);

        void lockPolicy(String caseId, LockPolicyRequest lockPolicyRequest);

        /**
         * Creates a Stripe Identity verification session.
         * Returns client secret for frontend to open Stripe UI.
         * Does NOT trigger STM transition - webhook will do that.
         */
        IdentityVerificationView createIdentitySession(String caseId);

}
