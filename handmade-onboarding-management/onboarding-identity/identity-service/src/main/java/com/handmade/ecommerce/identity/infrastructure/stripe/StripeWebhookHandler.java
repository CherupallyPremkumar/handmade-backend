package com.handmade.ecommerce.identity.infrastructure.stripe;

import com.handmade.ecommerce.identity.infrastructure.repository.IdentityVerificationRepository;
import com.handmade.ecommerce.identity.infrastructure.webhook.WebhookIdempotencyService;
import com.handmade.ecommerce.identity.domain.model.VerificationResult;
import com.handmade.ecommerce.identity.domain.model.IdentityVerificationSession;
import com.handmade.ecommerce.identity.domain.event.IdentityVerifiedEvent;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.identity.VerificationSession;
import com.stripe.net.Webhook;
import org.chenile.base.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles Stripe Identity webhooks
 * Processes verification session events asynchronously
 * 
 * PRODUCTION-GRADE IMPLEMENTATION:
 * - Constructor injection only (no @Value)
 * - Transactional idempotency (DB-first)
 * - Safe error handling (no retries on signature failure)
 * - No synchronous Stripe API calls
 * - Uses expanded webhook data only
 */
@Service
public class StripeWebhookHandler {

    private static final Logger logger = LoggerFactory.getLogger(StripeWebhookHandler.class);

    // Stripe event type constants
    private static final String EVENT_VERIFIED = "identity.verification_session.verified";
    private static final String EVENT_REQUIRES_INPUT = "identity.verification_session.requires_input";
    private static final String EVENT_CANCELED = "identity.verification_session.canceled";

    // Stripe status constants
    private static final String STATUS_VERIFIED = "verified";
    private static final String STATUS_REQUIRES_INPUT = "requires_input";
    private static final String STATUS_CANCELED = "canceled";

    private final IdentityVerificationRepository repository;
    private final WebhookIdempotencyService idempotencyService;
    private final String webhookSecret;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor injection ONLY - no @Value in service
     * Webhook secret injected via configuration
     */
    public StripeWebhookHandler(
            IdentityVerificationRepository repository,
            WebhookIdempotencyService idempotencyService,
            String webhookSecret,
            ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.idempotencyService = idempotencyService;
        this.webhookSecret = webhookSecret;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handle Stripe webhook (async processing)
     * 
     * CRITICAL: Returns immediately to Stripe with 200 OK
     * Processes in background to prevent webhook retries
     * 
     * ERROR HANDLING:
     * - Signature failure: Log and return (no retry)
     * - Duplicate event: Log and return (idempotent)
     * - Processing error: Rollback transaction, retry allowed
     */
    @Async
    @Transactional
    public void handleWebhook(String payload, String signatureHeader) {

        Event event;

        // 1. Verify Stripe signature FIRST
        try {
            event = Webhook.constructEvent(
                    payload, signatureHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            // CRITICAL: Do NOT throw - return 200 to prevent retries
            logger.warn("Invalid Stripe webhook signature - ignoring event");
            return;
        }

        // 2. Idempotency check BEFORE processing
        // Uses DB transaction - if processing fails, idempotency rolls back
        if (!idempotencyService.registerIfNew(event.getId(), event.getType())) {
            logger.info("Duplicate webhook event ignored: {}", event.getId());
            return;
        }

        // 3. Process event based on type
        try {
            processEvent(event);
        } catch (Exception e) {
            logger.error("Error processing webhook {} - transaction will rollback",
                    event.getId(), e);
            // Re-throw to rollback transaction (including idempotency)
            // Stripe will retry
            throw e;
        }
    }

    /**
     * Process event based on type
     * Isolated from webhook handling for clean separation
     */
    private void processEvent(Event event) {
        String eventType = event.getType();

        logger.info("Processing Stripe webhook event: {} (type: {})",
                event.getId(), eventType);

        switch (eventType) {
            case EVENT_VERIFIED -> handleVerified(event);
            case EVENT_REQUIRES_INPUT -> handleRequiresInput(event);
            case EVENT_CANCELED -> handleCanceled(event);
            default -> logger.debug("Ignoring webhook event type: {}", eventType);
        }
    }

    /**
     * Handle verified event
     * Uses ONLY data from webhook - no additional Stripe API calls
     */
    private void handleVerified(Event event) {
        VerificationSession stripeSession = extractSession(event);

        IdentityVerificationSession session = findSessionByExternalId(
                stripeSession.getId());

        // Map Stripe result to domain (using webhook data only)
        VerificationResult result = mapStripeResult(stripeSession);

        // Update session
        session.updateFromExternalWebhook(STATUS_VERIFIED, result);
        repository.save(session);

        logger.info("Publishing IdentityVerifiedEvent for case: {}",
                session.getOnboardingCaseId());

        eventPublisher.publishEvent(new IdentityVerifiedEvent(this,
                session.getOnboardingCaseId(), result));
    }

    /**
     * Handle requires_input event
     */
    private void handleRequiresInput(Event event) {
        VerificationSession stripeSession = extractSession(event);

        IdentityVerificationSession session = findSessionByExternalId(
                stripeSession.getId());

        session.updateFromExternalWebhook(STATUS_REQUIRES_INPUT, null);
        repository.save(session);

        logger.info("Identity verification requires input for case: {}",
                session.getOnboardingCaseId());
    }

    /**
     * Handle canceled event
     */
    private void handleCanceled(Event event) {
        VerificationSession stripeSession = extractSession(event);

        IdentityVerificationSession session = findSessionByExternalId(
                stripeSession.getId());

        VerificationResult result = new VerificationResult();
        result.setIdentityVerified(false);
        result.setFailureReason("Verification canceled by user");

        session.updateFromExternalWebhook(STATUS_CANCELED, result);
        repository.save(session);

        logger.info("Identity verification canceled for case: {}",
                session.getOnboardingCaseId());

        // TODO: Trigger onboarding STM event: IDENTITY_REJECTED
    }

    /**
     * Extract Stripe session from event
     * Safe extraction with clear error message
     */
    private VerificationSession extractSession(Event event) {
        return (VerificationSession) event
                .getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> new IllegalStateException(
                        "Missing Stripe session object in webhook event"));
    }

    /**
     * Find session by external session ID
     */
    private IdentityVerificationSession findSessionByExternalId(String externalSessionId) {
        return repository.findByExternalSessionId(externalSessionId)
                .orElseThrow(() -> new NotFoundException(
                        "Identity verification session not found for external ID: " + externalSessionId));
    }

    /**
     * Map Stripe verification result to domain
     * 
     * CRITICAL: Uses ONLY webhook data - NO additional Stripe API calls
     * Stripe webhooks contain all necessary data when properly expanded
     */
    private VerificationResult mapStripeResult(VerificationSession session) {
        VerificationResult result = new VerificationResult();

        // Use verified_outputs from webhook (already expanded by Stripe)
        if (session.getVerifiedOutputs() != null) {
            result.setIdentityVerified(true);
            result.setDocumentVerified(true);

            // Liveness check passed if session is verified
            result.setLivenessCheckPassed(
                    session.getStatus() != null &&
                            STATUS_VERIFIED.equals(session.getStatus()));
        } else {
            result.setIdentityVerified(false);
            result.setDocumentVerified(false);
            result.setLivenessCheckPassed(false);
        }

        // Map failure reason if available
        if (session.getLastError() != null) {
            result.setFailureReason(session.getLastError().getReason());
        }

        return result;
    }
}
