package com.handmade.ecommerce.stripe.api;

/**
 * Stripe Tax Service Interface
 * Handles W-9/W-8 tax form collection via Stripe Tax
 * 
 * Used by: seller-management for seller tax verification
 * Future: customer-management for 1099 recipients
 */
public interface StripeTaxService {
    
    /**
     * Create tax registration session
     * Returns URL to redirect user to Stripe-hosted tax form
     * 
     * @param entityId ID of entity (seller, customer, etc.)
     * @param entityType Type of entity (SELLER, CUSTOMER, etc.)
     * @param returnUrl URL to redirect back to after completion
     * @return Stripe-hosted tax form URL
     */
    String createTaxRegistrationSession(
        String entityId,
        String entityType,
        String returnUrl
    );
    
    /**
     * Retrieve tax registration status
     * 
     * @param registrationId Stripe tax registration ID
     * @return Tax registration result
     */
    StripeTaxRegistrationResult getTaxRegistrationStatus(String registrationId);
    
    /**
     * Check if entity has completed tax interview
     * 
     * @param registrationId Stripe tax registration ID
     * @return true if completed
     */
    boolean isTaxInterviewCompleted(String registrationId);
}
