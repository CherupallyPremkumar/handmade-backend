package com.handmade.ecommerce.cart.service;

import com.handmade.ecommerce.cart.model.AddCartItemRequest;
import com.handmade.ecommerce.cart.model.Cart;
import com.handmade.ecommerce.cart.model.UpdateCartItemRequest;

/**
 * Cart service for managing shopping carts.
 * 
 * Supports:
 * - Guest carts (sessionId)
 * - User carts (userId)
 * - Cart merging (guest → user after login)
 */
public interface CartService {

}
