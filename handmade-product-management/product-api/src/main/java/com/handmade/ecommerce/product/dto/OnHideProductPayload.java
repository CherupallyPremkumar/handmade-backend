package com.handmade.ecommerce.product.dto;

import com.handmade.ecommerce.event.domain.DomainEvent;
import org.chenile.workflow.param.MinimalPayload;

public class OnHideProductPayload extends MinimalPayload implements DomainEvent {
    private String productId;

    public OnHideProductPayload() {
    }

    @Override
    public String getEventId() { return java.util.UUID.randomUUID().toString(); }

    @Override
    public String getEventType() { return "HIDE_PRODUCT"; }

    @Override
    public java.time.LocalDateTime getOccurredAt() { return java.time.LocalDateTime.now(); }

    @Override
    public String getAggregateId() { return productId; }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
