package com.handmade.ecommerce.command.cart;

public class ModifyCustomerPayload {

    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public ModifyCustomerPayload setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }
}
