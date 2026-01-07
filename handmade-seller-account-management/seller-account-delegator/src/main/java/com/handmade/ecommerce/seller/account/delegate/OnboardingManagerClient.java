package com.handmade.ecommerce.seller.account.delegate;

import com.handmade.ecommerce.seller.account.dto.ActiveSellerAccountView;
import com.handmade.ecommerce.seller.account.dto.CreateSellerAccountRequest;
import com.handmade.ecommerce.seller.account.dto.LockPolicyRequest;

import java.util.Optional;

/**
 * Client for Onboarding Management
 */
public interface OnboardingManagerClient {

    String createCase(CreateSellerAccountRequest request);

    Optional<ActiveSellerAccountView> findActiveCase(String email, String country);

    void lockPolicy(String caseId, LockPolicyRequest lockPolicyRequest);

    boolean existsByEmail(String email);

}
