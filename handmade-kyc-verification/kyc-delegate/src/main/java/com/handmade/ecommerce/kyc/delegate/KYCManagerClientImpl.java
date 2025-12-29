package com.handmade.ecommerce.kyc.delegate;

import com.handmade.ecommerce.kyc.api.KYCVerificationResult;
import com.handmade.ecommerce.kyc.api.KYCVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * KYC Manager Client Implementation
 * Uses Chenile proxy to call remote KYC services
 */
@Component
public class KYCManagerClientImpl implements KYCManagerClient {
    
    @Autowired
    @Qualifier("kycVerificationServiceProxy")
    private KYCVerificationService kycVerificationService;
    
    @Override
    public <T> KYCVerificationResult verifyIdentity(T entity, String documentUploadId, String verifiedBy) {
        return kycVerificationService.verifyIdentity(entity, documentUploadId, verifiedBy);
    }
    
    @Override
    public String getProviderName() {
        return kycVerificationService.getProviderName();
    }
}
