package com.handmade.ecommerce.payment.service;

/**
 * Service for validating payment details (bank account, IFSC, etc.)
 * This is distinct from PaymentOnboardingService which handles gateway
 * onboarding.
 */
public interface PaymentValidationService {

    /**
     * Validates bank account number format.
     * 
     * @param accountNumber The account number to validate
     * @return true if valid format
     */
    boolean validateAccountNumber(String accountNumber);

    /**
     * Validates IFSC code format.
     * 
     * @param ifscCode The IFSC code to validate
     * @return true if valid format
     */
    boolean validateIfscCode(String ifscCode);
}
