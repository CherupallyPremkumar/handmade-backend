package com.handmade.ecommerce.event;

import com.handmade.ecommerce.event.model.DomainEvent;

public interface EventBus {
    <T extends DomainEvent> void publish(T event);
    <T extends DomainEvent> void register(Class<T> eventType, EventObserver<T> observer);
}
