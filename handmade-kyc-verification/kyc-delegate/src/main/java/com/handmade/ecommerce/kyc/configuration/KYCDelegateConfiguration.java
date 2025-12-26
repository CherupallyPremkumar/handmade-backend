package com.handmade.ecommerce.kyc.configuration;

import com.handmade.ecommerce.kyc.api.KYCVerificationService;
import org.chenile.http.api.ProxyBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * KYC Delegate Configuration
 * Configures Chenile HTTP proxies for remote KYC service calls
 */
@Configuration
public class KYCDelegateConfiguration {
    
    @Value("${kyc.service.base-url:http://localhost:8080}")
    private String kycServiceBaseUrl;
    
    @Bean
    public KYCVerificationService kycVerificationServiceProxy(ProxyBuilder proxyBuilder) {
        return proxyBuilder.build(
            KYCVerificationService.class,
            kycServiceBaseUrl + "/kyc/verification"
        );
    }
}
