package com.handmade.ecommerce.command.cart;

public class AddShipmentPayload {
    String addressId;

    public String getAddressId() {
        return addressId;
    }

    public AddShipmentPayload setAddressId(String addressId) {
        this.addressId = addressId;
        return this;
    }
}
