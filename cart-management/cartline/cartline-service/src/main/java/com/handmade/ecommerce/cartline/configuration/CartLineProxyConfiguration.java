package com.handmade.ecommerce.cartline.configuration;

import com.handmade.ecommerce.pricing.PricingService;
import org.chenile.core.context.HeaderCopier;
import org.chenile.proxy.builder.ProxyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartLineProxyConfiguration {


    @Autowired
    ProxyBuilder proxyBuilder;
    @Autowired
    HeaderCopier headerCopier;

    @Bean
    PricingService pricingService()
    {
        return proxyBuilder.buildProxy(PricingService.class,"pricingService",headerCopier, ProxyBuilder.ProxyMode.LOCAL,"");
    }
}
