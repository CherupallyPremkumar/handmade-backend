package com.handmade.ecommerce.product.service;

import com.handmade.ecommerce.product.dto.ExternalProductDto;
import java.util.Optional;
import java.util.List;

/**
 * ACL Interface: Defines contract for fetching data from Product Management.
 */
public interface ProductServiceClient {
    Optional<ExternalProductDto> getProduct(String productId);
    List<ExternalProductDto> getAllProducts();
}
