package com.handmade.ecommerce.coupon.model;

/**
 * Type of coupon
 */
public enum CouponType {
    /**
     * Platform-wide coupon (applies to entire cart)
     * Discount comes from platform revenue
     */
    PLATFORM,
    
    /**
     * Seller-specific coupon (applies to seller's items only)
     * Discount comes from seller's revenue
     */
    SELLER
}
