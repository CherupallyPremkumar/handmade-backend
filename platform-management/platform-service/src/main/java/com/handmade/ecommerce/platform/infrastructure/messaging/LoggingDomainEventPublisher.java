package com.handmade.ecommerce.platform.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Simple implementation that logs events.
 * Replace with Kafka/RabbitMQ publisher in real production environment.
 */
@Component
public class LoggingDomainEventPublisher implements DomainEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(LoggingDomainEventPublisher.class);

    @Override
    public void publish(Object event) {
        logger.info("DOMAIN EVENT PUBLISHED: {}", event);
    }
}
