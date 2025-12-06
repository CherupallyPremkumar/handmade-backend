package com.handmade.ecommerce.productorch.configuration;


import com.handmade.ecommerce.inventory.service.InventoryService;
import com.handmade.ecommerce.notification.service.NotificationService;

import com.handmade.ecommerce.pricing.PricingService;
import com.handmade.ecommerce.product.service.ProductService;
import org.chenile.core.context.HeaderCopier;
import org.chenile.proxy.builder.ProxyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Chenile Proxy Configuration for Product Orchestration Service
 * Creates proxies for dependent services
 */
@Configuration
public class ProductOrchestrationProxyConfig {
    
    @Autowired
    private ProxyBuilder proxyBuilder;
    
    @Autowired
    private HeaderCopier headerCopier;
    
    @Value("${services.product.url:#{null}}")
    private String productServiceUrl;
    
    @Value("${services.inventory.url:#{null}}")
    private String inventoryServiceUrl;
    
    @Value("${services.notification.url:#{null}}")
    private String notificationServiceUrl;
    
    @Value("${services.pricing.url:#{null}}")
    private String pricingServiceUrl;
    
    @Bean
    public ProductService productServiceProxy() {
        return proxyBuilder.buildProxy(
            ProductService.class,
            "productService",
            headerCopier,
            productServiceUrl
        );
    }
    
    @Bean
    public InventoryService inventoryServiceProxy() {
        return proxyBuilder.buildProxy(
            InventoryService.class,
            "inventoryService",
            headerCopier,
            inventoryServiceUrl
        );
    }
    
    @Bean
    public NotificationService notificationServiceProxy() {
        return proxyBuilder.buildProxy(
            NotificationService.class,
            "notificationService",
            headerCopier,
            notificationServiceUrl
        );
    }
    
    @Bean
    public PricingService pricingServiceProxy() {
        return proxyBuilder.buildProxy(
                PricingService.class,
            "pricingService",
            headerCopier,
            pricingServiceUrl
        );
    }
}
