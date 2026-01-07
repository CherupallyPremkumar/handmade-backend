package com.handmade.ecommerce.seller.account.event;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class ComplianceSnapshotCreatedEvent extends BaseActivationEvent {
    public ComplianceSnapshotCreatedEvent(String workflowId, String sellerId) {
        super(workflowId, sellerId);
    }
}
