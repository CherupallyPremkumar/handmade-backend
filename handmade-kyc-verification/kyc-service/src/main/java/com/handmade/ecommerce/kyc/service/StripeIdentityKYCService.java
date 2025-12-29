package com.handmade.ecommerce.kyc.service;

import com.handmade.ecommerce.kyc.api.KYCVerificationResult;
import com.handmade.ecommerce.kyc.api.KYCVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Stripe Identity KYC Verification Service
 * Phase 2: Automated verification using Stripe Identity API
 * 
 * Setup:
 * 1. Add Stripe dependency to kyc-service/pom.xml
 * 2. Set stripe.api.key in application.properties
 * 3. Change active KYC service in configuration
 * 
 * TODO: Implement when ready for Phase 2
 */
@Service("stripeKYCService")
public class StripeIdentityKYCService<T> implements KYCVerificationService<T> {
    
    private static final Logger logger = LoggerFactory.getLogger(StripeIdentityKYCService.class);
    
    @Value("${stripe.api.key:}")
    private String stripeApiKey;
    
    @Override
    public KYCVerificationResult verifyIdentity(T entity, String documentUploadId, String verifiedBy) {
        logger.info("Stripe Identity KYC verification for entity: {}", 
                   entity.getClass().getSimpleName());
        
        // TODO: Implement Stripe Identity API call
        throw new UnsupportedOperationException(
            "Stripe Identity integration not yet implemented. " +
            "Use ManualKYCService for now."
        );
    }
    
    @Override
    public String getProviderName() {
        return "STRIPE";
    }
    
    @Override
    public boolean isAutomated() {
        return true;
    }
}
