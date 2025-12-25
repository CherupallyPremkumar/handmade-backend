package com.handmade.ecommerce.event.service;

import com.handmade.ecommerce.event.EventBus;
import com.handmade.ecommerce.event.EventObserver;
import com.handmade.ecommerce.event.model.DomainEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class CoreEventBus  implements EventBus {
    private final Map<Class<?>, CopyOnWriteArrayList<EventObserver<?>>> observers = new ConcurrentHashMap<>();

    @Override
    public <T extends DomainEvent> void register(Class<T> eventType, EventObserver<T> observer) {
        observers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(observer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends DomainEvent> void publish(T event) {
        List<EventObserver<?>> list = observers.get(event.getClass());
        if (list != null) {
            for (EventObserver<?> obs : list) {
                try {
                    ((EventObserver<T>) obs).onEvent(event);
                } catch (Exception e) {
                    // log error but continue notifying other observers
                    System.err.println("Observer failed: " + e.getMessage());
                }
            }
        }
    }
}