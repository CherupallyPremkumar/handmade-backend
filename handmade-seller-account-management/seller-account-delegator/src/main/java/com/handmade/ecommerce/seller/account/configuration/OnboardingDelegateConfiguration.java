package com.handmade.ecommerce.seller.account.configuration;

import com.handmade.ecommerce.seller.account.api.SellerAccountService;
import com.handmade.ecommerce.seller.account.delegate.OnboardingManagerClient;
import com.handmade.ecommerce.seller.account.delegate.OnboardingManagerClientImpl;
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
    public SellerAccountService sellerAccountServiceProxy() {
        return proxyBuilder.buildProxy(
                SellerAccountService.class,
                "onboardingManager",
                null,
                baseUrl
        );
    }
}
