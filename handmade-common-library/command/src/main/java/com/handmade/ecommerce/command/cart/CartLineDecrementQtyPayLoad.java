package com.handmade.ecommerce.command.cart;


public class CartLineDecrementQtyPayLoad  {
    String cartLineId;

    public String getCartLineId() {
        return cartLineId;
    }

    public void setCartLineId(String cartLineId) {
        this.cartLineId = cartLineId;
    }
}
