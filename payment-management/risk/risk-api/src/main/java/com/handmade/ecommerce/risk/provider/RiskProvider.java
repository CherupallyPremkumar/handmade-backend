package com.handmade.ecommerce.risk.provider;

import com.handmade.ecommerce.risk.provider.model.RiskCheckRequest;
import com.handmade.ecommerce.risk.provider.model.RiskCheckResponse;

/**
 * Interface for risk assessment providers.
 * 
 * Implementations:
 * - SiftProvider (Sift.com)
 * - RiskifiedProvider (Riskified.com)
 * - StripeRadarProvider (Stripe Radar)
 * - InternalRiskProvider (Custom logic)
 * 
 * Similar to PaymentExecutor pattern - allows switching providers.
 */
public interface RiskProvider {
    
    /**
     * Checks risk for a transaction.
     * 
     * @param request Risk check request
     * @return Risk check response with score and level
     */
    RiskCheckResponse checkRisk(RiskCheckRequest request);
    
    /**
     * Returns the provider type.
     * 
     * @return Provider type (SIFT, RISKIFIED, STRIPE_RADAR, INTERNAL)
     */
    RiskProviderType getProviderType();
    
    /**
     * Validates provider signature (for webhooks).
     * 
     * @param payload Webhook payload
     * @param signature Webhook signature
     * @return true if signature is valid
     */
    boolean validateSignature(String payload, String signature);
}
