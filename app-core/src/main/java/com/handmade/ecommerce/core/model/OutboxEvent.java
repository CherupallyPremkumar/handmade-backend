package com.handmade.ecommerce.core.model;

import java.time.Instant;

/**
 * OutboxEvent - Transactional Outbox Pattern for reliable event publishing
 * Ensures events are published at-least-once with transactional guarantees
 */
@Entity
@Table(name = "outbox_events", indexes = {
    @Index(name = "idx_outbox_status", columnList = "status"),
    @Index(name = "idx_outbox_created_at", columnList = "created_at"),
    @Index(name = "idx_outbox_sequence", columnList = "sequence_number")
})
public class OutboxEvent {
    
    @Id
    @Column(name = "event_id", length = 50)
    private String eventId;
    
    @Column(name = "aggregate_id", length = 50, nullable = false)
    private String aggregateId;
    
    @Column(name = "aggregate_type", length = 100, nullable = false)
    private String aggregateType;
    
    @Column(name = "event_type", length = 100, nullable = false)
    private String eventType;
    
    @Column(name = "event_payload", columnDefinition = "TEXT", nullable = false)
    private String eventPayload; // JSON
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private OutboxStatus status;
    
    @Column(name = "retry_count")
    private Integer retryCount;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "published_at")
    private Instant publishedAt;
    
    @Column(name = "sequence_number")
    private Long sequenceNumber;
    
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
    
    // Constructors
    public OutboxEvent() {
        this.createdAt = Instant.now();
        this.status = OutboxStatus.PENDING;
        this.retryCount = 0;
    }
    
    public OutboxEvent(String eventId, String aggregateId, String aggregateType, 
                       String eventType, String eventPayload) {
        this();
        this.eventId = eventId;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.eventPayload = eventPayload;
    }
    
    // Business methods
    public void markAsPublished() {
        this.status = OutboxStatus.PUBLISHED;
        this.publishedAt = Instant.now();
    }
    
    public void markAsFailed(String errorMessage) {
        this.status = OutboxStatus.FAILED;
        this.errorMessage = errorMessage;
        this.retryCount++;
    }
    
    public void resetForRetry() {
        this.status = OutboxStatus.PENDING;
        this.retryCount++;
    }
    
    public boolean canRetry() {
        return retryCount < 3; // Max 3 retries
    }
    
    // Getters and Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    
    public String getAggregateId() { return aggregateId; }
    public void setAggregateId(String aggregateId) { this.aggregateId = aggregateId; }
    
    public String getAggregateType() { return aggregateType; }
    public void setAggregateType(String aggregateType) { this.aggregateType = aggregateType; }
    
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    
    public String getEventPayload() { return eventPayload; }
    public void setEventPayload(String eventPayload) { this.eventPayload = eventPayload; }
    
    public OutboxStatus getStatus() { return status; }
    public void setStatus(OutboxStatus status) { this.status = status; }
    
    public Integer getRetryCount() { return retryCount; }
    public void setRetryCount(Integer retryCount) { this.retryCount = retryCount; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
    
    public Long getSequenceNumber() { return sequenceNumber; }
    public void setSequenceNumber(Long sequenceNumber) { this.sequenceNumber = sequenceNumber; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    /**
     * Outbox Status Enum
     */
    public enum OutboxStatus {
        PENDING,
        PUBLISHED,
        FAILED
    }
}
