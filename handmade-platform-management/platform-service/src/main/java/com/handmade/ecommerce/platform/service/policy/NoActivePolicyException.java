package com.handmade.ecommerce.platform.service.policy;

/**
 * Exception thrown when no active onboarding policy exists
 * for a given country and seller type
 */
public class NoActivePolicyException extends RuntimeException {
    
    public NoActivePolicyException(String message) {
        super(message);
    }
}
