package com.handmade.ecommerce.platform.infrastructure.event;

import com.handmade.ecommerce.platform.application.port.out.DomainEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Spring implementation of DomainEventPublisher.
 * Infrastructure layer - publishes domain events using Spring ApplicationEventPublisher.
 * Events are published AFTER transaction commit to ensure consistency.
 */
@Component
public class SpringDomainEventPublisher implements DomainEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(SpringDomainEventPublisher.class);
    
    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(Object event) {
        if (event == null) {
            log.warn("Attempted to publish null event");
            return;
        }
        
        log.info("Publishing domain event: {}", event.getClass().getSimpleName());
        applicationEventPublisher.publishEvent(event);
    }
}
