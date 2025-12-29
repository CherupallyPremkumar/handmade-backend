package com.handmade.ecommerce.identity.infrastructure.webhook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Database-backed service for webhook idempotency handling.
 * Ensures webhook events are processed exactly once using database unique constraints.
 * 
 * PRODUCTION-GRADE:
 * - Uses database unique constraint for true idempotency
 * - Handles race conditions via DataIntegrityViolationException
 * - Transactional guarantees
 */
@Service
public class WebhookIdempotencyService {

    private static final Logger logger = LoggerFactory.getLogger(WebhookIdempotencyService.class);

    private final WebhookIdempotencyRepository repository;

    public WebhookIdempotencyService(WebhookIdempotencyRepository repository) {
        this.repository = repository;
    }

    /**
     * Register a webhook event if it's new.
     * Returns true if this is the first time seeing this event.
     * Returns false if the event was already processed (duplicate).
     * 
     * Uses REQUIRES_NEW propagation to commit immediately,
     * ensuring idempotency even if the main transaction rolls back.
     *
     * @param eventId The Stripe event ID
     * @param eventType The Stripe event type
     * @return true if registered successfully (new event), false if duplicate
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean registerIfNew(String eventId, String eventType) {
        try {
            // Try to insert - will fail if duplicate due to unique constraint
            WebhookIdempotencyRecord record = new WebhookIdempotencyRecord(eventId, eventType);
            repository.save(record);
            logger.debug("Registered new webhook event: {}", eventId);
            return true;
        } catch (DataIntegrityViolationException e) {
            // Duplicate event - unique constraint violation
            logger.debug("Duplicate webhook event detected: {}", eventId);
            return false;
        }
    }

    /**
     * Check if an event has already been processed.
     *
     * @param eventId The Stripe event ID
     * @return true if already processed, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean isProcessed(String eventId) {
        return repository.existsByEventId(eventId);
    }

    /**
     * Clean up old processed events.
     * Should be called periodically (e.g., via scheduled job).
     *
     * @param olderThan Remove events processed before this time
     * @return Number of records deleted
     */
    @Transactional
    public long cleanup(LocalDateTime olderThan) {
        long countBefore = repository.count();
        repository.deleteByProcessedAtBefore(olderThan);
        long countAfter = repository.count();
        long deleted = countBefore - countAfter;
        
        if (deleted > 0) {
            logger.info("Cleaned up {} old webhook idempotency records", deleted);
        }
        
        return deleted;
    }
}
