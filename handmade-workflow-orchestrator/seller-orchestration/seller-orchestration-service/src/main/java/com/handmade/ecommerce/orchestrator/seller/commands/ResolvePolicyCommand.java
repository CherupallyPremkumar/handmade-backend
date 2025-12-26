package com.handmade.ecommerce.orchestrator.seller.commands;

import com.handmade.ecommerce.platform.delegate.PlatformManagerClient;
import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Resolves onboarding policy from Platform Service
 * Spring bean name: resolve-policy-command
 */
@Component("resolve-policy-command")
public class ResolvePolicyCommand implements Command<Map<String, Object>> {
    
    private final PlatformManagerClient platformManagerClient;
    
    public ResolvePolicyCommand(PlatformManagerClient platformManagerClient) {
        this.platformManagerClient = platformManagerClient;
    }
    
    @Override
    public void execute(Map<String, Object> context) throws Exception {
        String countryCode = (String) context.get("countryCode");
        String sellerType = (String) context.get("sellerType");
        
        // Call Platform Service to resolve policy
        OnboardingPolicy policy = platformManagerClient.resolveOnboardingPolicy(countryCode, sellerType);
        
        // Store in context
        context.put("onboardingPolicy", policy);
        context.put("country", countryCode); // For routing
        
        // Evaluate which steps are required
        context.put("identityRequired", isStepRequired(policy, "identity_verification"));
        context.put("taxRequired", isStepRequired(policy, "tax_verification"));
        context.put("bankRequired", isStepRequired(policy, "bank_verification"));
    }
    
    private boolean isStepRequired(OnboardingPolicy policy, String stepName) {
        return policy.getRules().stream()
            .anyMatch(rule -> rule.getStepName().equals(stepName) && rule.isRequired());
    }
}
