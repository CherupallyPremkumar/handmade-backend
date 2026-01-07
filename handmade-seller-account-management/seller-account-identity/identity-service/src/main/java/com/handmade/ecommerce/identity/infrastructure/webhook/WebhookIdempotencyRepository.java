package com.handmade.ecommerce.identity.infrastructure.webhook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository for webhook idempotency records.
 */
@Repository
public interface WebhookIdempotencyRepository extends JpaRepository<WebhookIdempotencyRecord, Long> {

    /**
     * Find a record by event ID.
     *
     * @param eventId The Stripe event ID
     * @return Optional containing the record if found
     */
    Optional<WebhookIdempotencyRecord> findByEventId(String eventId);

    /**
     * Check if an event has been processed.
     *
     * @param eventId The Stripe event ID
     * @return true if the event exists in the database
     */
    boolean existsByEventId(String eventId);

    /**
     * Delete old records processed before a certain time.
     * Used for cleanup to prevent table growth.
     *
     * @param before Delete records processed before this time
     */
    void deleteByProcessedAtBefore(LocalDateTime before);
}
