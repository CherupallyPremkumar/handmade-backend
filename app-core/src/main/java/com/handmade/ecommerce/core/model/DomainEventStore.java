package com.handmade.ecommerce.core.model;

import java.time.Instant;

/**
 * DomainEventStore - Event Sourcing storage
 * Stores all domain events for audit trail and event replay
 */
@Entity
@Table(name = "domain_event_store", indexes = {
    @Index(name = "idx_event_aggregate", columnList = "aggregate_id, version"),
    @Index(name = "idx_event_type", columnList = "event_type"),
    @Index(name = "idx_event_occurred", columnList = "occurred_at")
})
public class DomainEventStore {
    
    @Id
    @Column(name = "event_id", length = 50)
    private String eventId;
    
    @Column(name = "aggregate_id", length = 50, nullable = false)
    private String aggregateId;
    
    @Column(name = "aggregate_type", length = 100, nullable = false)
    private String aggregateType;
    
    @Column(name = "event_type", length = 100, nullable = false)
    private String eventType;
    
    @Column(name = "event_data", columnDefinition = "TEXT", nullable = false)
    private String eventData; // JSON
    
    @Column(name = "version", nullable = false)
    private Long version;
    
    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;
    
    @Column(name = "user_id", length = 50)
    private String userId;
    
    @Column(name = "partition_key", length = 100)
    private String partitionKey;
    
    @Column(name = "stream_id", length = 100)
    private String streamId;
    
    // Constructors
    public DomainEventStore() {
        this.occurredAt = Instant.now();
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
    
    public String getEventData() { return eventData; }
    public void setEventData(String eventData) { this.eventData = eventData; }
    
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    
    public Instant getOccurredAt() { return occurredAt; }
    public void setOccurredAt(Instant occurredAt) { this.occurredAt = occurredAt; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getPartitionKey() { return partitionKey; }
    public void setPartitionKey(String partitionKey) { this.partitionKey = partitionKey; }
    
    public String getStreamId() { return streamId; }
    public void setStreamId(String streamId) { this.streamId = streamId; }
}
