package com.handmade.ecommerce.stripe.delegate;

import com.handmade.ecommerce.stripe.api.StripeTaxRegistrationResult;
import com.handmade.ecommerce.stripe.api.StripeTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Stripe Manager Client Implementation
 * Uses Chenile proxy to call remote Stripe services
 */
@Component
public class StripeManagerClientImpl implements StripeManagerClient {
    
    @Autowired
    @Qualifier("stripeTaxServiceProxy")
    private StripeTaxService stripeTaxService;
    
    @Override
    public String createTaxRegistrationSession(String entityId, String entityType, String returnUrl) {
        return stripeTaxService.createTaxRegistrationSession(entityId, entityType, returnUrl);
    }
    
    @Override
    public StripeTaxRegistrationResult getTaxRegistrationStatus(String registrationId) {
        return stripeTaxService.getTaxRegistrationStatus(registrationId);
    }
    
    @Override
    public boolean isTaxInterviewCompleted(String registrationId) {
        return stripeTaxService.isTaxInterviewCompleted(registrationId);
    }
}
