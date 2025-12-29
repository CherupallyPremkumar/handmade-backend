package com.handmade.ecommerce.orchestration.seller.event;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class PayoutProfileInitializedEvent extends BaseActivationEvent {
    public PayoutProfileInitializedEvent(String workflowId, String sellerId) {
        super(workflowId, sellerId);
    }
}
