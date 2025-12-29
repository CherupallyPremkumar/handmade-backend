package com.handmade.ecommerce.orchestration.product.service;

import com.handmade.ecommerce.orchestration.product.dto.ProductOrchestrationRequest;
import com.handmade.ecommerce.product.dto.ProductResponse;

/**
 * Orchestration service for complex product creation flows.
 */
public interface ProductOrchestrationService {
    ProductResponse orchestrateProduct(ProductOrchestrationRequest request);
}
