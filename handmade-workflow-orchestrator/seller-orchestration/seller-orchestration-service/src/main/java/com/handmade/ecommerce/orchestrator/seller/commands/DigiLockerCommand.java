package com.handmade.ecommerce.orchestrator.seller.commands;

import com.handmade.ecommerce.kyc.delegate.KYCManagerClient;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Starts DigiLocker verification (India)
 * Spring bean name: digilocker-command
 */
@Component("digilocker-command")
public class DigiLockerCommand implements Command<Map<String, Object>> {
    
    private final KYCManagerClient kycManagerClient;
    
    public DigiLockerCommand(KYCManagerClient kycManagerClient) {
        this.kycManagerClient = kycManagerClient;
    }
    
    @Override
    public void execute(Map<String, Object> context) throws Exception {
        String sellerId = (String) context.get("sellerId");
        Map<String, Object> config = getStepConfig(context, "identity_verification");
        
        // Start DigiLocker
        Map<String, String> result = kycManagerClient.startDigiLocker(sellerId, config);
        
        // Store session URL in context
        context.put("identitySessionUrl", result.get("sessionUrl"));
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, Object> getStepConfig(Map<String, Object> context, String stepName) {
        Map<String, Object> policy = (Map<String, Object>) context.get("onboardingPolicy");
        return (Map<String, Object>) policy.get(stepName + "Config");
    }
}
