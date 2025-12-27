package com.handmade.ecommerce.policy.configuration;

import com.handmade.ecommerce.policy.delegate.PolicyManagerClient;
import com.handmade.ecommerce.policy.delegate.PolicyManagerClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Policy Delegator
 * Sets up the PolicyManagerClient bean for remote service calls
 */
@Configuration
public class PolicyDelegateConfiguration {
    
    @Bean
    public PolicyManagerClient policyManagerClient() {
        return new PolicyManagerClientImpl();
    }
}
