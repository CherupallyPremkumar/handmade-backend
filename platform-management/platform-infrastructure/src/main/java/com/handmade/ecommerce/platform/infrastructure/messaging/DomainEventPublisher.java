package com.handmade.ecommerce.platform.infrastructure.messaging;

/**
 * Infrastructure interface for publishing domain events.
 * Implementations could use Kafka, RabbitMQ, or generic Spring Events.
 */
public interface DomainEventPublisher {
    void publish(Object event);
}
