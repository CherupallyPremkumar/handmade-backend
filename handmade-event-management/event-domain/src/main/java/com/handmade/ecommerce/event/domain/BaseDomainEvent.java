package com.handmade.ecommerce.event.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseDomainEvent implements DomainEvent {

    private String eventId;
    private String eventType;
    private LocalDateTime occurredAt;
    private String aggregateId;

    protected BaseDomainEvent(String eventType, String aggregateId) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.aggregateId = aggregateId;
        this.occurredAt = LocalDateTime.now();
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String getAggregateId() {
        return aggregateId;
    }
}
