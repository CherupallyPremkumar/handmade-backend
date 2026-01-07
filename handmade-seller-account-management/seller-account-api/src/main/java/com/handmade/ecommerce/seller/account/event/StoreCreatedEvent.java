package com.handmade.ecommerce.seller.account.event;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class StoreCreatedEvent extends BaseActivationEvent {
    private final String storeId;

    public StoreCreatedEvent(String workflowId, String sellerId, String storeId) {
        super(workflowId, sellerId);
        this.storeId = storeId;
    }
}
