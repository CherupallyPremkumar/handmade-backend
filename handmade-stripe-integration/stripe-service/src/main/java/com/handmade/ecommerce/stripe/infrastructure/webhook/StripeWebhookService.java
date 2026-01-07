package com.handmade.ecommerce.stripe.infrastructure.webhook;

import com.handmade.ecommerce.identity.domain.event.ExternalIdentityUpdateEvent;
import com.handmade.ecommerce.identity.domain.model.VerificationResult;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.identity.VerificationSession;
import com.stripe.net.Webhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Service for handling Stripe webhooks.
 * Validates signatures and publishes generic domain events.
 * Confines Stripe model mapping to this integration layer.
 */
@Service("_identityWebhookService_")
public class StripeWebhookService {

    private static final Logger logger = LoggerFactory.getLogger(StripeWebhookService.class);

    private static final String EVENT_IDENTITY_VERIFIED = "identity.verification_session.verified";
    private static final String EVENT_IDENTITY_REQUIRES_INPUT = "identity.verification_session.requires_input";
    private static final String EVENT_IDENTITY_CANCELED = "identity.verification_session.canceled";

    private static final String EVENT_TAX_COMPLETED = "tax.registration.completed";
    private static final String EVENT_TAX_FAILED = "tax.registration.failed";

    private static final String STATUS_VERIFIED = "verified";
    private static final String STATUS_REQUIRES_INPUT = "requires_input";
    private static final String STATUS_CANCELED = "canceled";
    private static final String STATUS_FAILED = "failed";

    @Value("${stripe.webhook.secret:}")
    private String webhookSecret;

    private final ApplicationEventPublisher eventPublisher;

    public StripeWebhookService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Entry point for Chenile controller.
     * Processes raw Stripe webhook payload.
     */
    public void handleIdentityWebhook(String payload, String signatureHeader) {
        Event event;

        // 1. Verify Stripe signature
        try {
            event = Webhook.constructEvent(payload, signatureHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            logger.warn("Invalid Stripe webhook signature - ignoring event");
            return;
        }

        // 2. Map Stripe event to generic domain event
        processEvent(event);
    }

    private void processEvent(Event event) {
        String eventType = event.getType();
        logger.info("Processing Stripe webhook event: {} (type: {})", event.getId(), eventType);

        if (eventType.startsWith("identity.verification_session")) {
            handleIdentityEvent(event);
        } else if (eventType.startsWith("tax.registration")) {
            handleTaxEvent(event);
        } else {
            logger.debug("Ignoring non-integrated webhook event type: {}", eventType);
        }
    }

    private void handleIdentityEvent(Event event) {
        VerificationSession stripeSession = (VerificationSession) event.getDataObjectDeserializer().getObject()
                .orElse(null);
        if (stripeSession == null)
            return;

        String genericStatus;
        VerificationResult result = null;

        switch (event.getType()) {
            case EVENT_IDENTITY_VERIFIED -> {
                genericStatus = STATUS_VERIFIED;
                result = mapStripeResult(stripeSession);
            }
            case EVENT_IDENTITY_REQUIRES_INPUT -> genericStatus = STATUS_REQUIRES_INPUT;
            case EVENT_IDENTITY_CANCELED -> {
                genericStatus = STATUS_CANCELED;
                result = new VerificationResult();
                result.setIdentityVerified(false);
                result.setFailureReason("Verification canceled by user");
            }
            default -> {
                return;
            }
        }

        eventPublisher.publishEvent(new ExternalIdentityUpdateEvent(
                this, stripeSession.getId(), genericStatus, result));
    }

    private void handleTaxEvent(Event event) {
        // Since Registration might be in different package/version, use generic map
        // extraction if needed
        // but with 26.1.0 it should be available.
        com.stripe.model.tax.Registration registration = (com.stripe.model.tax.Registration) event
                .getDataObjectDeserializer().getObject().orElse(null);

        if (registration == null)
            return;

        // Extraction from raw JSON since typed method is missing in this SDK version
        // for Tax Registration
        String entityId = null;
        String entityType = null;

        if (registration.getRawJsonObject() != null && registration.getRawJsonObject().has("metadata")) {
            com.google.gson.JsonObject metadata = registration.getRawJsonObject().getAsJsonObject("metadata");
            if (metadata.has("entity_id"))
                entityId = metadata.get("entity_id").getAsString();
            if (metadata.has("entity_type"))
                entityType = metadata.get("entity_type").getAsString();
        }

        String status = registration.getStatus();

        eventPublisher.publishEvent(new com.handmade.ecommerce.seller.account.domain.event.ExternalTaxUpdateEvent(
                this, registration.getId(), status, entityId, entityType));
    }

    private VerificationResult mapStripeResult(VerificationSession session) {
        VerificationResult result = new VerificationResult();
        if (session.getVerifiedOutputs() != null) {
            result.setIdentityVerified(true);
            result.setDocumentVerified(true);
            result.setLivenessCheckPassed(STATUS_VERIFIED.equals(session.getStatus()));
        } else {
            result.setIdentityVerified(false);
            result.setDocumentVerified(false);
            result.setLivenessCheckPassed(false);
        }

        if (session.getLastError() != null) {
            result.setFailureReason(session.getLastError().getReason());
        }
        return result;
    }
}
