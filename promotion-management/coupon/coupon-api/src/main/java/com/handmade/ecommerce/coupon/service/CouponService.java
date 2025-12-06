package com.handmade.ecommerce.coupon.service;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.coupon.model.Coupon;

/**
 * Service for coupon validation and discount calculation
 */
public interface CouponService {
    
    /**
     * Validate and retrieve coupon by code
     * @param couponCode The coupon code
     * @param userId User ID applying the coupon
     * @return Valid coupon
     * @throws CouponNotFoundException if coupon doesn't exist
     * @throws CouponExpiredException if coupon is expired
     * @throws CouponUsageLimitExceededException if usage limit exceeded
     */
    Coupon validateCoupon(String couponCode, String userId);
    
    /**
     * Calculate discount amount for given coupon and amount
     * @param coupon The coupon
     * @param amount The amount to apply discount on
     * @return Discount amount
     */
    Money calculateDiscount(Coupon coupon, Money amount);
    
    /**
     * Check if coupon is applicable to cart
     * @param coupon The coupon
     * @param cartTotal Cart total amount
     * @return true if applicable
     */
    boolean isApplicable(Coupon coupon, Money cartTotal);
    
    /**
     * Mark coupon as used
     * @param couponCode The coupon code
     */
    void markAsUsed(String couponCode);
    
    /**
     * Get coupon by code
     * @param couponCode The coupon code
     * @return Coupon or null
     */
    Coupon getCouponByCode(String couponCode);
}
