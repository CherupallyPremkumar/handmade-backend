package com.handmade.ecommerce.event.domain;

import java.time.LocalDateTime;

public interface DomainEvent {
    String getEventId();

    String getEventType();

    LocalDateTime getOccurredAt();

    String getAggregateId();
}
