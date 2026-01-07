package com.handmade.ecommerce.identity.infrastructure.webhook;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity for tracking processed webhook events (idempotency).
 * Ensures each webhook event is processed exactly once.
 */
@Entity
@Table(name = "webhook_idempotency",
        uniqueConstraints = @UniqueConstraint(columnNames = "event_id"))
@Getter
@Setter
public class WebhookIdempotencyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false, unique = true, length = 255)
    private String eventId;

    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt;

    @Column(name = "event_type", length = 100)
    private String eventType;

    public WebhookIdempotencyRecord() {
    }

    public WebhookIdempotencyRecord(String eventId, String eventType) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.processedAt = LocalDateTime.now();
    }
}
