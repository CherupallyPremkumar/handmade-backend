package com.handmade.ecommerce.payment.service;

import com.handmade.ecommerce.payment.dto.BankAccountDetails;
import com.handmade.ecommerce.payment.dto.BankVerificationResult;

/**
 * Main service interface for bank account verification
 * Orchestrates validation and verification using appropriate strategies
 */
public interface BankAccountVerificationService {

    /**
     * Validate bank account format (local validation, no API call)
     * 
     * @param bankDetails Bank account details to validate
     * @return true if format is valid, false otherwise
     */
    boolean validateFormat(BankAccountDetails bankDetails);

    /**
     * Verify bank account with payment gateway (API call)
     * Automatically selects appropriate strategy based on country code
     * 
     * @param bankDetails Bank account details to verify
     * @return Verification result with reference ID
     * @throws Exception if verification fails
     */
    BankVerificationResult verifyAccount(BankAccountDetails bankDetails) throws Exception;

    /**
     * Verify bank account using specific provider
     * 
     * @param bankDetails  Bank account details to verify
     * @param providerName Provider name (RAZORPAY, STRIPE)
     * @return Verification result with reference ID
     * @throws Exception if verification fails
     */
    BankVerificationResult verifyAccountWithProvider(BankAccountDetails bankDetails, String providerName)
            throws Exception;

    /**
     * Get verification status by reference ID
     * 
     * @param referenceId Reference ID from payment gateway
     * @return Current verification status
     * @throws Exception if status check fails
     */
    BankVerificationResult getVerificationStatus(String referenceId) throws Exception;

    /**
     * Get verification history for a seller
     * 
     * @param sellerId Seller ID
     * @return List of verification results
     */
    java.util.List<BankVerificationResult> getVerificationHistory(String sellerId);

    /**
     * Check if account has been verified before
     * 
     * @param accountNumber Account number
     * @return true if previously verified, false otherwise
     */
    boolean isAccountVerified(String accountNumber);
}
