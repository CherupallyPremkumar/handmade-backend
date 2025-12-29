package com.handmade.ecommerce.orchestration.seller.event;

import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@ToString(callSuper = true)
public class SellerActivatedEvent extends BaseActivationEvent {
    private final LocalDateTime activatedAt;

    public SellerActivatedEvent(String workflowId, String sellerId) {
        super(workflowId, sellerId);
        this.activatedAt = LocalDateTime.now();
    }
}
