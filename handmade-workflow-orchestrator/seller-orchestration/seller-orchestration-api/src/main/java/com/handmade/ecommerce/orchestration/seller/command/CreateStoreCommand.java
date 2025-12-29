package com.handmade.ecommerce.orchestration.seller.command;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class CreateStoreCommand extends BaseActivationCommand {
    public CreateStoreCommand(String workflowId, String sellerId) {
        super(workflowId, sellerId);
    }
}
