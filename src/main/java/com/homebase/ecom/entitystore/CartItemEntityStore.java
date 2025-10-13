package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.CartItem;
import com.homebase.ecom.repository.CartItemRepository;
import org.chenile.utils.entity.service.EntityStore;

public class CartItemEntityStore implements EntityStore<CartItem> {

    private final CartItemRepository cartItemRepository;

    public CartItemEntityStore(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void store(CartItem entity) {

    }

    @Override
    public CartItem retrieve(String id) {
        return null;
    }
}
