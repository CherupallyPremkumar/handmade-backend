package com.handmade.ecommerce.command.cart;


public class UpdateCartLinePayload {

    String cartLineId;
    private int quantity;



    public int getQuantity() {
        return quantity;
    }

    public UpdateCartLinePayload setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getCartLineId() {
        return cartLineId;
    }

    public void setCartLineId(String cartLineId) {
        this.cartLineId = cartLineId;
    }
}
