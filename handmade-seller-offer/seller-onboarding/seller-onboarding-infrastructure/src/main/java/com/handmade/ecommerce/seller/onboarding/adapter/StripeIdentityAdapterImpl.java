package com.handmade.ecommerce.seller.onboarding.adapter;

import com.handmade.ecommerce.seller.onboarding.port.StripeIdentityAdapter;
import com.handmade.ecommerce.seller.onboarding.dto.StripeConfigResponse;
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
