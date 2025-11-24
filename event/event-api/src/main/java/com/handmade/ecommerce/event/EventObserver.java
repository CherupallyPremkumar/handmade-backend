package com.handmade.ecommerce.event;

import com.handmade.ecommerce.event.model.DomainEvent;

public interface EventObserver<T extends DomainEvent> {
    void onEvent(T event);
}
