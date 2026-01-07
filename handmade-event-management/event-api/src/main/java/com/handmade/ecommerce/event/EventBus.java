package com.handmade.ecommerce.event;

import com.handmade.ecommerce.event.api.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventBus {

    @Autowired
    private EventPublisher eventPublisher;

    private Map<Class<?>, List<Object>> subscribers = new HashMap<>();

    public void publish(Object event) {
        // Map event class to a topic string for the underlying publisher
        String topic = event.getClass().getSimpleName();
        eventPublisher.publish(topic, event);
    }

    public void register(Class<?> eventType, Object subscriber) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(subscriber);
    }
}
