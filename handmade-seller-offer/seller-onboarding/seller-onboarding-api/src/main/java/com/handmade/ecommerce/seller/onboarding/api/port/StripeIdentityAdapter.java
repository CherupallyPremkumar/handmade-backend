package com.handmade.ecommerce.seller.onboarding.api.port;

import com.handmade.ecommerce.seller.onboarding.api.dto.StripeConfigResponse;

public interface StripeIdentityAdapter {
    StripeConfigResponse createVerificationSession(String caseId, String sellerId);
}
