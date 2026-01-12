package com.handmade.ecommerce.seller.onboarding.port;

import com.handmade.ecommerce.seller.onboarding.dto.StripeConfigResponse;

public interface StripeIdentityAdapter {
    StripeConfigResponse createVerificationSession(String caseId, String sellerId);
}
