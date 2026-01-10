package com.handmade.ecommerce.seller.onboarding.infrastructure.adapter;

import com.handmade.ecommerce.seller.onboarding.api.port.StripeIdentityAdapter;
import com.handmade.ecommerce.seller.onboarding.api.dto.StripeConfigResponse;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class StripeIdentityAdapterImpl implements StripeIdentityAdapter {

    @Override
    public StripeConfigResponse createVerificationSession(String caseId, String sellerId) {
        // Mock implementation for now
        return StripeConfigResponse.builder()
                .clientSecret("pi_mock_" + UUID.randomUUID().toString())
                .publicKey("pk_test_mock")
                .flowId("flow_mock_" + UUID.randomUUID().toString())
                .build();
    }
}
