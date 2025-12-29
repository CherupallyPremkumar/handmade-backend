package com.handmade.ecommerce.product.dto;

import com.handmade.ecommerce.event.domain.DomainEvent;
import org.chenile.workflow.param.MinimalPayload;

/**
    Customized Payload for the addVariant event.
*/
public class AddVariantProductPayload extends MinimalPayload implements DomainEvent {

    @Override
    public String getEventId() { return java.util.UUID.randomUUID().toString(); }

    @Override
    public String getEventType() { return "ADD_VARIANT"; }

    @Override
    public java.time.LocalDateTime getOccurredAt() { return java.time.LocalDateTime.now(); }

    @Override
    public String getAggregateId() { return null; } // Should be populated by caller
}
