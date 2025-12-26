package com.handmade.ecommerce.order.config;

import com.handmade.ecommerce.payment.service.PaymentService;
import com.handmade.ecommerce.notification.service.NotificationService;

import org.chenile.core.context.HeaderCopier;
import org.chenile.proxy.builder.ProxyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Chenile Proxy Configuration for Order Service
 * Creates proxies for dependent services (Payment, Inventory, Notification)
 * Proxies automatically route to local or remote services based on
 * configuration
 */
@Configuration
public class ProxyConfiguration {

    @Autowired
    private ProxyBuilder proxyBuilder;

    @Autowired
    private HeaderCopier headerCopier;

    @Value("${services.payment.url:#{null}}")
    private String paymentServiceUrl;

    @Value("${services.inventory.url:#{null}}")
    private String inventoryServiceUrl;

    @Value("${services.notification.url:#{null}}")
    private String notificationServiceUrl;

    /**
     * Create PaymentService proxy
     * - If paymentService bean exists locally → uses local
     * - Otherwise → uses HTTP remote call to paymentServiceUrl
     */
    @Bean
    public PaymentService paymentServiceProxy() {
        return proxyBuilder.buildProxy(
                PaymentService.class,
                "paymentService", // Service name (must match @Service bean name)
                headerCopier, // Copies HTTP headers for context propagation
                paymentServiceUrl // Base URL for remote calls (null = auto-detect)
        );
    }

    /**
     * Create InventoryService proxy
     * - If inventoryService bean exists locally → uses local
     * - Otherwise → uses HTTP remote call to inventoryServiceUrl
     */
    @Bean
    public InventoryService inventoryServiceProxy() {
        return proxyBuilder.buildProxy(
                InventoryService.class,
                "inventoryService",
                headerCopier,
                inventoryServiceUrl);
    }

    /**
     * Create NotificationService proxy
     * - If notificationService bean exists locally → uses local
     * - Otherwise → uses HTTP remote call to notificationServiceUrl
     */
    @Bean
    public NotificationService notificationServiceProxy() {
        return proxyBuilder.buildProxy(
                NotificationService.class,
                "notificationService",
                headerCopier,
                notificationServiceUrl);
    }
}
