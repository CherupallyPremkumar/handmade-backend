package com.handmade.ecommerce.sellerorch;

import com.handmade.ecommerce.payment.service.PaymentService;
import com.handmade.ecommerce.product.service.ProductService;
import com.handmade.ecommerce.notification.service.NotificationService;
import org.chenile.base.proxy.ProxyBuilder;
import org.chenile.http.handler.HeaderCopier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Chenile Proxy Configuration for Seller Orchestration Service
 * Creates proxies for dependent services
 */
@Configuration
public class SellerOrchestrationProxyConfig {
    
    @Autowired
    private ProxyBuilder proxyBuilder;
    
    @Autowired
    private HeaderCopier headerCopier;
    
    @Value("${services.payment.url:#{null}}")
    private String paymentServiceUrl;
    
    @Value("${services.product.url:#{null}}")
    private String productServiceUrl;
    
    @Value("${services.notification.url:#{null}}")
    private String notificationServiceUrl;
    
    @Bean
    public PaymentService paymentServiceProxy() {
        return proxyBuilder.buildProxy(
            PaymentService.class,
            "paymentService",
            headerCopier,
            paymentServiceUrl
        );
    }
    
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
    public NotificationService notificationServiceProxy() {
        return proxyBuilder.buildProxy(
            NotificationService.class,
            "notificationService",
            headerCopier,
            notificationServiceUrl
        );
    }
}
