package com.handmade.ecommerce.kyc.delegate;

import com.handmade.ecommerce.kyc.api.KYCVerificationResult;
import com.handmade.ecommerce.kyc.api.KYCVerificationService;

/**
 * KYC Manager Client Interface
 * Remote client for calling KYC verification services
 * 
 * Used by: seller-service, customer-service, etc.
 */
public interface KYCManagerClient {
    
    /**
     * Verify identity for any entity type
     * Delegates to KYCVerificationService
     */
    <T> KYCVerificationResult verifyIdentity(
        T entity,
        String documentUploadId,
        String verifiedBy
    );
    
    /**
     * Get KYC provider name
     */
    String getProviderName();
}
