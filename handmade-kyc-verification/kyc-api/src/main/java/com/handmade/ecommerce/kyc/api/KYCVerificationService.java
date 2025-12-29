package com.handmade.ecommerce.kyc.api;

/**
 * KYC Verification Service Interface
 * Abstraction layer for identity verification providers
 * 
 * Can be used for:
 * - Seller verification
 * - Customer verification  
 * - Admin verification
 * - Partner verification
 * 
 * Implementations:
 * - ManualKYCService (Phase 1: Admin review)
 * - StripeIdentityKYCService (Phase 2: Stripe Identity)
 * - JumioKYCService (Phase 3: Jumio)
 */
public interface KYCVerificationService<T> {
    
    /**
     * Verify identity documents
     * 
     * @param entity The entity to verify (SellerAccount, Customer, Admin, etc.)
     * @param documentUploadId ID of uploaded documents
     * @param verifiedBy Who is performing verification (admin user ID or "SYSTEM")
     * @return KYCVerificationResult with verification status
     */
    KYCVerificationResult verifyIdentity(T entity, String documentUploadId, String verifiedBy);
    
    /**
     * Get provider name
     * @return Provider name (MANUAL, STRIPE, JUMIO, etc.)
     */
    String getProviderName();
    
    /**
     * Check if provider supports automatic verification
     * @return true if automated, false if manual
     */
    boolean isAutomated();
}
