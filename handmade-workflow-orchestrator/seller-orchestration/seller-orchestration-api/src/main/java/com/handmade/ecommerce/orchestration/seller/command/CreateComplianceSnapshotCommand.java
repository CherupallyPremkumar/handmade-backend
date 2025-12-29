package com.handmade.ecommerce.orchestration.seller.command;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class CreateComplianceSnapshotCommand extends BaseActivationCommand {
    public CreateComplianceSnapshotCommand(String workflowId, String sellerId) {
        super(workflowId, sellerId);
    }
}
