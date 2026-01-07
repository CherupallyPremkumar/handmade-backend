package com.handmade.ecommerce.identity.domain.event;

import com.handmade.ecommerce.identity.domain.model.VerificationResult;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event published when an external identity provider (e.g., Stripe) sends an update
 * via a webhook. This event is vendor-agnostic.
 */
@Getter
public class ExternalIdentityUpdateEvent extends ApplicationEvent {
    private final String externalSessionId;
    private final String status;
    private final VerificationResult result;

    public ExternalIdentityUpdateEvent(Object source, String externalSessionId, String status, VerificationResult result) {
        super(source);
        this.externalSessionId = externalSessionId;
        this.status = status;
        this.result = result;
    }
}
