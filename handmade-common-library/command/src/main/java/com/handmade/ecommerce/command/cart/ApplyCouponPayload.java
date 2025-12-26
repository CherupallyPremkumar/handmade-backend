package com.handmade.ecommerce.command.cart;

/**
 * Payload for applying coupon to cart
 */
public class ApplyCouponPayload {

    private String couponCode;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
