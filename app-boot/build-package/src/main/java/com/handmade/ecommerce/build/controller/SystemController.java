package com.handmade.ecommerce.build.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Health check and system information REST controller
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", "Handmade E-commerce Platform");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "0.0.1-SNAPSHOT");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Handmade E-commerce Platform");
        response.put("description", "Multi-tenant marketplace for handmade products");
        response.put("version", "0.0.1-SNAPSHOT");

        Map<String, String> modules = new HashMap<>();
        modules.put("user-management", "User lifecycle and workflow");
        modules.put("customer-management", "Customer business data");
        modules.put("product-management", "Product catalog and variants");
        modules.put("inventory-management", "Stock and warehouse management");
        modules.put("order-management", "Sales and purchase orders");
        modules.put("payment-management", "Payment processing");
        modules.put("cart-management", "Shopping cart");
        modules.put("security-management", "Authentication and authorization");
        modules.put("seller-management", "Seller and artisan profiles");
        modules.put("commission-management", "Fee structures and tracking");
        modules.put("content-management", "CMS for pages and media");

        response.put("modules", modules);
        return ResponseEntity.ok(response);
    }
}
