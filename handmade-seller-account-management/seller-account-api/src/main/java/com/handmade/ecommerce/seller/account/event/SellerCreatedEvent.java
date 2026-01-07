package com.handmade.ecommerce.seller.account.event;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class SellerCreatedEvent extends BaseActivationEvent {
    public SellerCreatedEvent(String workflowId, String sellerId) {
        super(workflowId, sellerId);
    }
}
