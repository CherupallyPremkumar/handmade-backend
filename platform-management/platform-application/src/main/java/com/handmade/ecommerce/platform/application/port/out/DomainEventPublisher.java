package com.handmade.ecommerce.platform.application.port.out;

/**
 * Port for publishing domain events to external systems.
 * Infrastructure will implement this (Kafka, RabbitMQ, Spring Events, etc.)
 */
public interface DomainEventPublisher {
    /**
     * Publish a domain event to the event bus.
     * @param event The domain event to publish
     */
    void publish(Object event);
}
