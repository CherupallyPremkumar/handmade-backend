package com.handmade.ecommerce.stripe.infrastructure.identity;

import com.handmade.ecommerce.identity.domain.gateway.ExternalIdentityGateway;
import com.handmade.ecommerce.identity.domain.model.ExternalVerificationSession;
import com.handmade.ecommerce.identity.domain.model.IdentityVerificationSession;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.identity.VerificationSession;
import com.stripe.param.identity.VerificationSessionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Stripe implementation of the ExternalIdentityGateway.
 * Confines Stripe SDK usage to the integration module.
 */
@Service
public class StripeExternalIdentityGateway implements ExternalIdentityGateway {

    private static final Logger logger = LoggerFactory.getLogger(StripeExternalIdentityGateway.class);

    @Value("${stripe.api.key:}")
    private String stripeApiKey;

    @Override
    public ExternalVerificationSession createVerificationSession(IdentityVerificationSession session) {
        Stripe.apiKey = stripeApiKey;

        // Build metadata for tracking
        Map<String, String> metadata = new HashMap<>();
        metadata.put("onboarding_case_id", session.getOnboardingCaseId());
        metadata.put("seller_email", session.getSellerEmail());

        try {
            // Create verification session parameters
            VerificationSessionCreateParams params = VerificationSessionCreateParams.builder()
                    .setType(VerificationSessionCreateParams.Type.DOCUMENT)
                    .putAllMetadata(metadata)
                    .setOptions(
                            VerificationSessionCreateParams.Options.builder()
                                    .setDocument(
                                            VerificationSessionCreateParams.Options.Document.builder()
                                                    .setRequireLiveCapture(true)
                                                    .setRequireIdNumber(true)
                                                    .setRequireMatchingSelfie(true)
                                                    .build())
                                    .build())
                    .build();

            // Call Stripe API
            VerificationSession stripeSession = VerificationSession.create(params);

            logger.info("Created Stripe verification session: {} for onboarding case: {}",
                    stripeSession.getId(), session.getOnboardingCaseId());

            return new ExternalVerificationSession(
                    stripeSession.getId(),
                    stripeSession.getClientSecret(),
                    stripeSession.getUrl());
        } catch (StripeException e) {
            logger.error("Error creating Stripe verification session for case: {}",
                    session.getOnboardingCaseId(), e);
            throw new RuntimeException("Failed to create identity verification session", e);
        }
    }

    @Override
    public boolean isConfigured() {
        return stripeApiKey != null && !stripeApiKey.isEmpty();
    }
}
