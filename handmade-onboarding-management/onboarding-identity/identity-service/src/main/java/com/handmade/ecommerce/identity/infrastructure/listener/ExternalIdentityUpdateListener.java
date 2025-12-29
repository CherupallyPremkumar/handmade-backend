package com.handmade.ecommerce.identity.infrastructure.listener;

import com.handmade.ecommerce.identity.domain.event.ExternalIdentityUpdateEvent;
import com.handmade.ecommerce.identity.domain.event.IdentityVerifiedEvent;
import com.handmade.ecommerce.identity.domain.model.IdentityVerificationSession;
import com.handmade.ecommerce.identity.infrastructure.repository.IdentityVerificationRepository;
import org.chenile.base.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Listener for external identity updates.
 * Processes generic ExternalIdentityUpdateEvents and updates the local domain.
 */
@Component
public class ExternalIdentityUpdateListener {

    private static final Logger logger = LoggerFactory.getLogger(ExternalIdentityUpdateListener.class);

    private final IdentityVerificationRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public ExternalIdentityUpdateListener(
            IdentityVerificationRepository repository,
            ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Async
    @EventListener
    @Transactional
    public void onExternalIdentityUpdate(ExternalIdentityUpdateEvent event) {
        logger.info("Received external identity update for session: {} with status: {}",
                event.getExternalSessionId(), event.getStatus());

        IdentityVerificationSession session = repository.findByExternalSessionId(event.getExternalSessionId())
                .orElseThrow(() -> new NotFoundException(
                        "Identity verification session not found for external ID: " + event.getExternalSessionId()));

        // Update session from webhook data
        session.updateFromExternalWebhook(event.getStatus(), event.getResult());
        repository.save(session);

        // If verified, publish the internal domain event
        if ("verified".equals(event.getStatus())) {
            logger.info("Publishing internal IdentityVerifiedEvent for case: {}",
                    session.getOnboardingCaseId());
            eventPublisher.publishEvent(new IdentityVerifiedEvent(this,
                    session.getOnboardingCaseId(), event.getResult()));
        }
    }
}
