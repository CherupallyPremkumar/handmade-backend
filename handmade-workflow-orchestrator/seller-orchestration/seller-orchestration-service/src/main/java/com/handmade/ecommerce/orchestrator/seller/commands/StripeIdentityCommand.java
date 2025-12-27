package com.handmade.ecommerce.orchestrator.seller.commands;

// TODO: Uncomment when KYC delegate is implemented
// import com.handmade.ecommerce.kyc.delegate.KYCManagerClient;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * Starts Stripe Identity verification (US)
 * Spring bean name: stripe-identity-command
 * 
 * NOTE: Currently stubbed - KYC delegate not yet implemented
 */
@Component("stripe-identity-command")
public class StripeIdentityCommand implements Command<Map<String, Object>> {
    
    // TODO: Inject KYCManagerClient when available
    // private final KYCManagerClient kycManagerClient;
    // 
    // public StripeIdentityCommand(KYCManagerClient kycManagerClient) {
    //     this.kycManagerClient = kycManagerClient;
    // }
    
    @Override
    public void execute(Map<String, Object> context) throws Exception {
        String sellerId = (String) context.get("sellerId");
        // Map<String, Object> config = getStepConfig(context, "identity_verification");
        
        // TODO: Replace with actual KYC service call
        // Map<String, String> result = kycManagerClient.startStripeIdentity(sellerId, config);
        
        // Stub implementation - simulate successful verification
        String sessionId = "vs_" + UUID.randomUUID().toString();
        String sessionUrl = "https://verify.stripe.com/start/" + sessionId;
        
        // Store session URL in context
        context.put("identitySessionUrl", sessionUrl);
        context.put("identitySessionId", sessionId);
        context.put("identityProvider", "stripe");
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, Object> getStepConfig(Map<String, Object> context, String stepName) {
        Map<String, Object> policy = (Map<String, Object>) context.get("onboardingPolicy");
        // Extract step config from policy
        return (Map<String, Object>) policy.get(stepName + "Config");
    }
}
