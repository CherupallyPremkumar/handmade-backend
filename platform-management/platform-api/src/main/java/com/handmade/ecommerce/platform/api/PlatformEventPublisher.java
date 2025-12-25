package com.handmade.ecommerce.platform.api;

public interface PlatformEventPublisher {
    
    void publishEvent(String topic, Object event);
}
