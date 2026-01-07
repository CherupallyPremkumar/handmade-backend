package com.handmade.ecommerce.policy.exception;

public class PricingPolicyNotFoundException extends RuntimeException {
    public PricingPolicyNotFoundException(String message) {
        super(message);
    }
}
