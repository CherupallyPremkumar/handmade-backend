package com.handmade.ecommerce.product.service;

import com.handmade.ecommerce.product.dto.CreateProductRequest;
import com.handmade.ecommerce.product.dto.ProductResponse;

import java.util.List;

/**
 * Product Service Interface
 * Used by both implementation and Chenile Proxy
 */
public interface ProductService {

    /**
     * Create a new product
     * 
     * @param request Product creation details
     * @return Created product
     */
    ProductResponse createProduct(CreateProductRequest request);

    /**
     * Get product by ID
     * 
     * @param productId Product identifier
     * @return Product details
     */
    ProductResponse getProduct(String productId);

    /**
     * Update product
     * 
     * @param productId Product identifier
     * @param request   Updated product details
     * @return Updated product
     */
    ProductResponse updateProduct(String productId, CreateProductRequest request);

    /**
     * Get products by seller
     * 
     * @param sellerId Seller identifier
     * @return List of products
     */
    List<ProductResponse> getProductsBySeller(String sellerId);
}
