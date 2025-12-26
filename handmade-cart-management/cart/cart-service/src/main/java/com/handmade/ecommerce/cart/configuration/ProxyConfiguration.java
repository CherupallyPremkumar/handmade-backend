package com.handmade.ecommerce.cart.configuration;

import com.handmade.ecommerce.cartline.service.CartlineService;
import org.chenile.core.context.HeaderCopier;
import org.chenile.proxy.builder.ProxyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyConfiguration {

    @Autowired
    ProxyBuilder proxyBuilder;
    @Autowired
    HeaderCopier headerCopier;

    @Bean
    CartlineService cartlineService()
    {
        return new ProxyBuilder().buildProxy(CartlineService.class,"cartlineService",headerCopier,"");
    }
}
