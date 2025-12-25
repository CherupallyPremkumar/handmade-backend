package com.handmade.ecommerce.event.domain;

import java.time.LocalDateTime;

public abstract class DomainEvent {
    
    public String eventId;
    public String eventType;
    public LocalDateTime occurredAt;
    public String aggregateId;
    
    protected DomainEvent(String eventType, String aggregateId) {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.eventType = eventType;
        this.aggregateId = aggregateId;
        this.occurredAt = LocalDateTime.now();
    }
}
