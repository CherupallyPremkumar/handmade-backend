package com.handmade.ecommerce.catalog.service.integration;

import java.util.Optional;

/**
 * ACL Interface: Defines contract for fetching data from Product Management
 */
public interface ProductServiceClient {
    Optional<ExternalProductDto> getProduct(String productId);

    /**
     * Fetch a batch of products for rule evaluation.
     * In a real system, this would use pagination/streaming.
     */
    java.util.List<ExternalProductDto> getAllProducts();
}
