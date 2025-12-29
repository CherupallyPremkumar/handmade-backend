package com.handmade.ecommerce.product.delegate;

import com.handmade.ecommerce.product.dto.CreateProductRequest;
import com.handmade.ecommerce.product.dto.ProductResponse;

import java.util.List;

/**
 * Client interface for interacting with the Product Service.
 * Used by orchestration and other modules to decouple from the service
 * implementation.
 */
public interface ProductManagerClient {
    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse getProduct(String productId);

    ProductResponse updateProduct(String productId, CreateProductRequest request);

    List<ProductResponse> getProductsBySeller(String sellerId);
}
