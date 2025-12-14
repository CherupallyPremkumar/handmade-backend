package com.handmade.ecommerce.cartline.service.impl;

/**
 * Cartline STM Event IDs
 * Defines all event IDs used in cartline state machine
 */
public final class CartlineEvent {

    public static final String INCREMENT_QTY = "increment_qty";
    public static final String DECREMENT_QTY = "decrement_qty";

    private CartlineEvent() {
        // Prevent instantiation
    }

    /**
     * Update cartline quantity
     */
    public static final String UPDATE_QTY = "update";

    /**
     * Delete cartline
     */
    public static final String DELETE = "delete";

    /**
     * Close cartline
     */
    public static final String CLOSE = "close";

    /**
     * Change cart (move cartline to different cart)
     */
    public static final String CHANGE_CART = "change_cart";
}
