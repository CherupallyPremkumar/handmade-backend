package com.handmade.ecommerce.platform.configuration;

import com.handmade.ecommerce.platform.delegate.PlatformManagerClient;
import com.handmade.ecommerce.platform.delegate.PlatformManagerClientImpl;
import com.handmade.ecommerce.platform.service.PlatformService;
import org.chenile.proxy.builder.ProxyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Configuration for Platform Delegate
 * Creates beans for remote platform management delegation
 */
@Configuration
public class PlatformDelegateConfiguration {
    
    @Autowired
    ProxyBuilder proxyBuilder;
    
    @Value("${platform.manager.base-url}")
    String baseUrl;
    
    @Bean
    public PlatformManagerClient platformManagerClient() {
        return new PlatformManagerClientImpl();
    }

    @Bean
    public PlatformService platformServiceProxy() {
        return proxyBuilder.buildProxy(
                PlatformService.class,
                "platformService",
                null,
                baseUrl
        );
    }
}
