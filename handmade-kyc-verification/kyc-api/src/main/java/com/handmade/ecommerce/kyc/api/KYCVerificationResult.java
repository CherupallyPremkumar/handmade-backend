package com.handmade.ecommerce.kyc.api;

import lombok.Data;

/**
 * KYC Verification Result
 * Returned by all KYC providers (manual, Stripe, Jumio, etc.)
 * Used across seller, customer, and admin verification
 */
@Data
public class KYCVerificationResult {
    
    private boolean verified;
    private double confidenceScore; // 0.0 to 1.0
    private String provider; // MANUAL, STRIPE, JUMIO, etc.
    private String verificationId; // Provider's verification ID
    private String verifiedBy; // Admin user ID or "SYSTEM"
    private String failureReason;
    private boolean requiresManualReview;
    
    // Document details extracted
    private String documentType;
    private String documentNumber;
    private boolean selfieMatched;
    private boolean addressProofValid;
}
