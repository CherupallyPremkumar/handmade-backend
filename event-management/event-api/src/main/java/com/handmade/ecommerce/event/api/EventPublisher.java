package com.handmade.ecommerce.event.api;

public interface EventPublisher {
    
    void publish(String topic, Object event);
}
