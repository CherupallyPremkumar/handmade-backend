package com.homebase.ecom.listeners;

import com.homebase.ecom.listeners.event.CartItemUpdatedEvent;
import com.homebase.ecom.service.CartStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CartItemEventListener {

    @Autowired
    private CartStateService cartStateService;

    @EventListener
    public void onCartItemUpdated(CartItemUpdatedEvent event) {
        cartStateService.refreshCart(event.getCartId());
    }
}