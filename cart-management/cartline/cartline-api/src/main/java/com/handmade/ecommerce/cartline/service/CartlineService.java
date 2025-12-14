package com.handmade.ecommerce.cartline.service;

import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.command.cart.AddCartLinePayload;
import com.handmade.ecommerce.command.cart.CartLineDecrementQtyPayLoad;
import com.handmade.ecommerce.command.cart.CartLineIncrementQtyPayLoad;
import com.handmade.ecommerce.command.cart.UpdateCartLinePayload;

import java.util.List;

/**
 * Cartline service
 * Manages individual cart line items
 */
public interface CartlineService {

    /**
     * Create cart line from payload
     */
    void createCartLine(AddCartLinePayload payload);

    /**
     * Decrement cart line quantity
     */
    void decrementCartLine(CartLineDecrementQtyPayLoad payLoad);

    /**
     * Increment cart line quantity
     */
    void incrementCartLine(CartLineIncrementQtyPayLoad payLoad);

    /**
     * Update cart line
     */
    void updateCartLine(UpdateCartLinePayload payload);

    /**
     * Get all cart lines for a cart
     */
    List<Cartline> getCartLines(String cartId);

    /**
     * Get specific cart line by ID
     */
    Cartline getCartLine(String cartLineId);

    /**
     * Delete single cart item
     */
    void deleteCartItem(String cartLineId);

    /**
     * Delete all cart lines for a cart
     */
    void deleteCartLines(String cartId);

    /**
     * Merge cart - move all cartlines from old cart to new cart
     */
    void mergeCart(String oldCartId, String newCartId);
}
