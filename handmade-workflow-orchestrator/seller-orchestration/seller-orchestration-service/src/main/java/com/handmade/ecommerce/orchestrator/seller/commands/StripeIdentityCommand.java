package com.handmade.ecommerce.orchestrator.seller.commands;

import com.handmade.ecommerce.kyc.delegate.KYCManagerClient;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Starts Stripe Identity verification (US)
 * Spring bean name: stripe-identity-command
 */
@Component("stripe-identity-command")
public class StripeIdentityCommand implements Command<Map<String, Object>> {
    
    private final KYCManagerClient kycManagerClient;
    
    public StripeIdentityCommand(KYCManagerClient kycManagerClient) {
        this.kycManagerClient = kycManagerClient;
    }
    
    @Override
    public void execute(Map<String, Object> context) throws Exception {
        String sellerId = (String) context.get("sellerId");
        Map<String, Object> config = getStepConfig(context, "identity_verification");
        
        // Start Stripe Identity
        Map<String, String> result = kycManagerClient.startStripeIdentity(sellerId, config);
        
        // Store session URL in context
        context.put("identitySessionUrl", result.get("sessionUrl"));
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, Object> getStepConfig(Map<String, Object> context, String stepName) {
        Map<String, Object> policy = (Map<String, Object>) context.get("onboardingPolicy");
        // Extract step config from policy
        return (Map<String, Object>) policy.get(stepName + "Config");
    }
}
