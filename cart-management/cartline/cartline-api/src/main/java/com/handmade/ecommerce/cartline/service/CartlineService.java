package com.handmade.ecommerce.cartline.service;

import com.handmade.ecommerce.cartline.model.Cartline;

import java.util.List;

/**
 * Cartline service (Amazon-style - simple CRUD)
 * 
 * Note: Most cart item operations go through CartService
 * This service is mainly for querying cart items
 */
public interface CartlineService {

    /**
     * Get all items for a cart
     */
    List<Cartline> getCartItems(String cartId);

    /**
     * Get specific cart item
     */
    Cartline getCartItem(String cartLineId);

    /**
     * Delete cart item
     */
    void deleteCartItem(String cartLineId);
}
