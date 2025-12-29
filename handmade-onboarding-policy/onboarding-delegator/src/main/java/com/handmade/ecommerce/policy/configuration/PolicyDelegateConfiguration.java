package com.handmade.ecommerce.policy.configuration;

import com.handmade.ecommerce.policy.delegate.OnboardingPolicyManagerClient;
import com.handmade.ecommerce.policy.delegate.OnboardingPolicyManagerClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Policy Delegator
 * Sets up the OnboardingPolicyManagerClient bean for remote service calls
 */
@Configuration
public class PolicyDelegateConfiguration {
    
    @Bean
    public OnboardingPolicyManagerClient onboardingPolicyManagerClient() {
        return new OnboardingPolicyManagerClientImpl();
    }
}
