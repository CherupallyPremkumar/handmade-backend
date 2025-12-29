package com.handmade.ecommerce.platform;

public interface PlatformEventPublisher {
    
    void publishEvent(String topic, Object event);
}
