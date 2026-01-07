package com.handmade.ecommerce.policy.service;

/**
 * Exception thrown when no active onboarding policy exists
 * for a given country and seller type
 */
public class NoActivePolicyException extends RuntimeException {
    
    public NoActivePolicyException(String message) {
        super(message);
    }
}
