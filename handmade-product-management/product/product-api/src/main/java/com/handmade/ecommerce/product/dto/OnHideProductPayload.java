package com.handmade.ecommerce.product.dto;

import com.handmade.ecommerce.event.model.DomainEvent;
import org.chenile.workflow.param.MinimalPayload;

public class OnHideProductPayload extends MinimalPayload implements DomainEvent {
    private String productId;

    public OnHideProductPayload() {
    }

    public OnHideProductPayload(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
