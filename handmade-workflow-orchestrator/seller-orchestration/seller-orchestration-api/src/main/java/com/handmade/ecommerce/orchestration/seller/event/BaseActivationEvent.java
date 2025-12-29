package com.handmade.ecommerce.orchestration.seller.event;

import lombok.Getter;
import lombok.ToString;
import java.io.Serializable;

@Getter
@ToString
public abstract class BaseActivationEvent implements Serializable {
    private final String workflowId;
    private final String sellerId;

    protected BaseActivationEvent(String workflowId, String sellerId) {
        this.workflowId = workflowId;
        this.sellerId = sellerId;
    }
}
