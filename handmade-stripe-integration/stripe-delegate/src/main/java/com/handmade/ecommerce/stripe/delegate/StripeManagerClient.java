package com.handmade.ecommerce.stripe.delegate;

import com.handmade.ecommerce.stripe.api.StripeTaxRegistrationResult;
import com.handmade.ecommerce.stripe.api.StripeTaxService;

/**
 * Stripe Manager Client Interface
 * Remote client for calling Stripe integration services
 * 
 * Used by: seller-service, payment-service, etc.
 */
public interface StripeManagerClient {
    
    /**
     * Create tax registration session
     * Delegates to StripeTaxService
     */
    String createTaxRegistrationSession(
        String entityId,
        String entityType,
        String returnUrl
    );
    
    /**
     * Get tax registration status
     * Delegates to StripeTaxService
     */
    StripeTaxRegistrationResult getTaxRegistrationStatus(String registrationId);
    
    /**
     * Check if tax interview completed
     * Delegates to StripeTaxService
     */
    boolean isTaxInterviewCompleted(String registrationId);
}
