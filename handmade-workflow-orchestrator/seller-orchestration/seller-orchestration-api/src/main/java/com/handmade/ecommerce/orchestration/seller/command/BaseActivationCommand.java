package com.handmade.ecommerce.orchestration.seller.command;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public abstract class BaseActivationCommand implements Serializable {
    private final String workflowId;
    private final String sellerId;

    protected BaseActivationCommand(String workflowId, String sellerId) {
        this.workflowId = workflowId;
        this.sellerId = sellerId;
    }
}
