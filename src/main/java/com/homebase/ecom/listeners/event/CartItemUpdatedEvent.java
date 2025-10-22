package com.homebase.ecom.listeners.event;

import org.springframework.context.ApplicationEvent;

public class CartItemUpdatedEvent extends ApplicationEvent {
    private final String cartId;
    private final String cartItemId;

    public CartItemUpdatedEvent(Object source, String cartId, String cartItemId) {
        super(source);
        this.cartId = cartId;
        this.cartItemId = cartItemId;
    }

    public String getCartId() { return cartId; }
    public String getCartItemId() { return cartItemId; }
}
