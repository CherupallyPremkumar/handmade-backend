package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Cart;
import com.homebase.ecom.repository.CartRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class CartEntityStore implements EntityStore<Cart> {

    private final CartRepository cartRepository;

    public CartEntityStore(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void store(Cart entity) {

    }

    @Override
    public Cart retrieve(String id) {
        return null;
    }
}
