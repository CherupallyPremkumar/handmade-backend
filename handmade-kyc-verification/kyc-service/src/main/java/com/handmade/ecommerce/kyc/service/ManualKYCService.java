package com.handmade.ecommerce.kyc.service;

import com.handmade.ecommerce.kyc.api.KYCVerificationResult;
import com.handmade.ecommerce.kyc.api.KYCVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Manual KYC Verification Service
 * Phase 1: Admin manually reviews documents and approves/rejects
 * 
 * Generic implementation that works for any entity type
 * (SellerAccount, Customer, Admin, etc.)
 */
@Service("manualKYCService")
public class ManualKYCService<T> implements KYCVerificationService<T> {
    
    private static final Logger logger = LoggerFactory.getLogger(ManualKYCService.class);
    
    @Override
    public KYCVerificationResult verifyIdentity(T entity, String documentUploadId, String verifiedBy) {
        logger.info("Manual KYC verification for entity: {}, verified by: {}", 
                   entity.getClass().getSimpleName(), verifiedBy);
        
        KYCVerificationResult result = new KYCVerificationResult();
        
        // In manual mode, admin has already reviewed documents
        // This is called after admin clicks "Approve" in dashboard
        result.setVerified(true);
        result.setConfidenceScore(1.0); // Manual review is 100% confident
        result.setProvider("MANUAL");
        result.setVerifiedBy(verifiedBy);
        result.setRequiresManualReview(false); // Already manually reviewed
        
        logger.info("Manual KYC verification complete for {}: verified=true", 
                   entity.getClass().getSimpleName());
        
        return result;
    }
    
    @Override
    public String getProviderName() {
        return "MANUAL";
    }
    
    @Override
    public boolean isAutomated() {
        return false;
    }
}
