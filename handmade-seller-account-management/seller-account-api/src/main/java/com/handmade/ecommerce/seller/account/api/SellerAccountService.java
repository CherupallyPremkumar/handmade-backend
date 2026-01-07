package com.handmade.ecommerce.seller.account.api;

import com.handmade.ecommerce.identity.dto.IdentityVerificationView;
import com.handmade.ecommerce.seller.account.dto.ActiveSellerAccountView;
import com.handmade.ecommerce.seller.account.dto.CreateSellerAccountRequest;
import com.handmade.ecommerce.seller.account.dto.LockPolicyRequest;
import java.util.Optional;

/**
 * Service interface for SellerAccount (Onboarding)
 */
public interface SellerAccountService {
        boolean existsByEmail(String email);

        String createCase(CreateSellerAccountRequest request);

        Optional<ActiveSellerAccountView> findActiveCase(String email, String country);

        void lockPolicy(String caseId, LockPolicyRequest lockPolicyRequest);

        /**
         * Creates a Stripe Identity verification session.
         * Returns client secret for frontend to open Stripe UI.
         * Does NOT trigger STM transition - webhook will do that.
         */
        IdentityVerificationView createIdentitySession(String caseId);

}
