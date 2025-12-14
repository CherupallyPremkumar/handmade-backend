package com.handmade.ecommerce.command.cart;



public class DeleteCartLinePayload{

    private String cartLineId;

    public String getOrderLineId() {
        return cartLineId;
    }

    public void setOrderLineId(String cartLineId) {
        this.cartLineId = cartLineId;
    }
}
