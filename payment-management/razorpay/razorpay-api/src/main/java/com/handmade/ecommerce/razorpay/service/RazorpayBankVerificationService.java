package com.handmade.ecommerce.razorpay.service;

import com.handmade.ecommerce.payment.dto.BankAccountDetails;
import com.handmade.ecommerce.payment.dto.BankVerificationResult;

/**
 * Razorpay-specific bank verification service
 * Handles penny drop verification for Indian bank accounts
 */
public interface RazorpayBankVerificationService {
    
    /**
     * Verify bank account using Razorpay Fund Account Validation API
     * 
     * @param bankDetails Bank account details (must include IFSC code)
     * @return Verification result with Razorpay reference ID
     * @throws Exception if verification fails
     */
    BankVerificationResult verifyBankAccount(BankAccountDetails bankDetails) throws Exception;
    
    /**
     * Get verification status from Razorpay
     * 
     * @param referenceId Razorpay fund account validation ID
     * @return Current verification status
     * @throws Exception if status check fails
     */
    BankVerificationResult getVerificationStatus(String referenceId) throws Exception;
    
    /**
     * Get the cost of penny drop verification
     * 
     * @return Cost in INR
     */
    double getPennyDropCost();
    
    /**
     * Check if Razorpay is configured and ready
     * 
     * @return true if configured, false otherwise
     */
    boolean isConfigured();
}
