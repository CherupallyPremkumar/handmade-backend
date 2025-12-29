package com.handmade.ecommerce.onboarding.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Domain Event published when an external tax verification update is received.
 * Decouples onboarding logic from specific tax providers (like Stripe).
 */
@Getter
public class ExternalTaxUpdateEvent extends ApplicationEvent {
    private final String externalRegistrationId;
    private final String status;
    private final String entityId;
    private final String entityType;

    public ExternalTaxUpdateEvent(Object source, String externalRegistrationId, String status, String entityId,
            String entityType) {
        super(source);
        this.externalRegistrationId = externalRegistrationId;
        this.status = status;
        this.entityId = entityId;
        this.entityType = entityType;
    }
}
