package com.handmade.ecommerce.payment.service.provider;

import com.handmade.ecommerce.payment.dto.BankAccountDetails;
import com.handmade.ecommerce.payment.dto.BankVerificationResult;

/**
 * Provider interface for bank account verification
 * Implemented by specific payment gateway adapters (Razorpay, Stripe)
 */
public interface BankVerificationProvider {

    /**
     * Verify bank account details with payment gateway
     * 
     * @param bankDetails Bank account details to verify
     * @return Verification result with reference ID
     * @throws Exception if verification fails
     */
    BankVerificationResult verify(BankAccountDetails bankDetails) throws Exception;

    /**
     * Get the name of the payment provider
     * 
     * @return Provider name (RAZORPAY, STRIPE, etc.)
     */
    String getProviderName();

    /**
     * Check if this provider supports the given country
     * 
     * @param countryCode ISO 2-letter country code
     * @return true if supported, false otherwise
     */
    boolean supportsCountry(String countryCode);

    /**
     * Get verification status by reference ID
     * 
     * @param referenceId Reference ID from previous verification
     * @return Current verification status
     * @throws Exception if status check fails
     */
    BankVerificationResult getVerificationStatus(String referenceId) throws Exception;

    /**
     * Get the cost of verification in the provider's currency
     * 
     * @return Cost per verification
     */
    double getVerificationCost();
}
