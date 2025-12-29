package com.handmade.ecommerce.onboarding.configuration;

import com.handmade.ecommerce.onboarding.api.OnboardingCaseService;
import com.handmade.ecommerce.onboarding.delegate.OnboardingManagerClient;
import com.handmade.ecommerce.onboarding.delegate.OnboardingManagerClientImpl;
import jakarta.persistence.Column;
import org.chenile.proxy.builder.ProxyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnboardingDelegateConfiguration {

     @Autowired
     ProxyBuilder proxyBuilder;
    
    @Value("${onboarding.manager.base-url}")
    String baseUrl;
    
    @Bean
    public OnboardingManagerClient onboardingManagerClient() {
        return new OnboardingManagerClientImpl();
    }

    @Bean
    public OnboardingCaseService onboardingCaseServiceProxy() {
        return proxyBuilder.buildProxy(
                OnboardingCaseService.class,
                "onboardingManager",
                null,
                baseUrl
        );
    }
}
