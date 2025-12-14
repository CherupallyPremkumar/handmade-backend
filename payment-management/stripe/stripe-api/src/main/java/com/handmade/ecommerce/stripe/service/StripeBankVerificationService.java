package com.handmade.ecommerce.stripe.service;

import com.handmade.ecommerce.payment.dto.BankAccountDetails;
import com.handmade.ecommerce.payment.dto.BankVerificationResult;

/**
 * Stripe-specific bank verification service
 * Handles micro-deposit and instant verification for international bank accounts
 */
public interface StripeBankVerificationService {
    
    /**
     * Verify bank account using Stripe Bank Account Verification API
     * 
     * @param bankDetails Bank account details (must include routing number for USA)
     * @return Verification result with Stripe reference ID
     * @throws Exception if verification fails
     */
    BankVerificationResult verifyBankAccount(BankAccountDetails bankDetails) throws Exception;
    
    /**
     * Get verification status from Stripe
     * 
     * @param referenceId Stripe bank account ID
     * @return Current verification status
     * @throws Exception if status check fails
     */
    BankVerificationResult getVerificationStatus(String referenceId) throws Exception;
    
    /**
     * Get the cost of verification for a specific country
     * 
     * @param countryCode ISO 2-letter country code
     * @return Cost in local currency
     */
    double getVerificationCost(String countryCode);
    
    /**
     * Check if Stripe is configured and ready
     * 
     * @return true if configured, false otherwise
     */
    boolean isConfigured();
    
    /**
     * Check if country is supported
     * 
     * @param countryCode ISO 2-letter country code
     * @return true if supported, false otherwise
     */
    boolean supportsCountry(String countryCode);
}
