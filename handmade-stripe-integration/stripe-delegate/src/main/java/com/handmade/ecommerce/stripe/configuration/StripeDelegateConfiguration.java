package com.handmade.ecommerce.stripe.configuration;

import com.handmade.ecommerce.stripe.api.StripeTaxService;
import org.chenile.proxy.ProxyBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Stripe Delegate Configuration
 * Configures Chenile HTTP proxies for remote Stripe service calls
 */
@Configuration
public class StripeDelegateConfiguration {
    
    @Value("${stripe.service.base-url:http://localhost:8080}")
    private String stripeServiceBaseUrl;
    
    @Bean
    public StripeTaxService stripeTaxServiceProxy(ProxyBuilder proxyBuilder) {
        return proxyBuilder.build(
            StripeTaxService.class,
            stripeServiceBaseUrl + "/stripe/tax"
        );
    }
}
