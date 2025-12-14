package com.handmade.ecommerce.sellerorch.configuration;


import com.handmade.ecommerce.tax.service.TaxService;
import org.chenile.core.context.HeaderCopier;
import org.chenile.proxy.builder.ProxyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class SellerOrchProxyConfig {

    @Autowired
    ProxyBuilder proxyBuilder;

    @Autowired
    private HeaderCopier headerCopier;

    @Value("${services.tax.url:#{null}}")
    private String taxServiceUrl;

    @Bean
    public TaxService TaxServiceProxy() {
        return proxyBuilder.buildProxy(
                TaxService.class,
                "paymentService", // Service name (must match @Service bean name)
                headerCopier, // Copies HTTP headers for context propagation
                taxServiceUrl // Base URL for remote calls (null = auto-detect)
        );
    }
}
