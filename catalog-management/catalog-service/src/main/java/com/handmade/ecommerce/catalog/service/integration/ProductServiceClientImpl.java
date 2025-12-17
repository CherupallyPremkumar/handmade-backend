package com.handmade.ecommerce.catalog.service.integration;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Mock implementation of ProductServiceClient for compilation/demo
 */
@Service
public class ProductServiceClientImpl implements ProductServiceClient {

    @Override
    public Optional<ExternalProductDto> getProduct(String productId) {
        // In real app, calling Product Service via Feign/WebClient
        // Simulating a response
        return Optional.of(new ExternalProductDto(
                productId,
                "Simulated Product " + productId,
                new BigDecimal("49.99"),
                true));
    }

    @Override
    public java.util.List<ExternalProductDto> getAllProducts() {
        // Simulating a small batch of products for the demo
        return java.util.Arrays.asList(
                new ExternalProductDto("prod-001", "Summer Dress", new BigDecimal("45.00"), true),
                new ExternalProductDto("prod-002", "Winter Jacket", new BigDecimal("150.00"), true),
                new ExternalProductDto("prod-003", "Cheap Accessories", new BigDecimal("10.00"), true),
                new ExternalProductDto("prod-004", "Premium Handbag", new BigDecimal("500.00"), true),
                new ExternalProductDto("prod-005", "Inactive Item", new BigDecimal("20.00"), false));
    }
}
