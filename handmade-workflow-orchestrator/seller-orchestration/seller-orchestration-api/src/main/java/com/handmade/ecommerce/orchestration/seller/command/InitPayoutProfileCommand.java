package com.handmade.ecommerce.orchestration.seller.command;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class InitPayoutProfileCommand extends BaseActivationCommand {
    public InitPayoutProfileCommand(String workflowId, String sellerId) {
        super(workflowId, sellerId);
    }
}
