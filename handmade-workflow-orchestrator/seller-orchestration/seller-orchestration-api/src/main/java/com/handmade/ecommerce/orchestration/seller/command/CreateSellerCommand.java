package com.handmade.ecommerce.orchestration.seller.command;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class CreateSellerCommand extends BaseActivationCommand {
    private final String email;
    private final String country;

    public CreateSellerCommand(String workflowId, String sellerId, String email, String country) {
        super(workflowId, sellerId);
        this.email = email;
        this.country = country;
    }
}
