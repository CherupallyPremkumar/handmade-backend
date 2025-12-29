package com.handmade.ecommerce.product.delegate;

import com.handmade.ecommerce.product.dto.CreateProductRequest;
import com.handmade.ecommerce.product.dto.ProductResponse;
import com.handmade.ecommerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductManagerClientImpl implements ProductManagerClient {

    @Autowired
    private ProductService productService;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @Override
    public ProductResponse getProduct(String productId) {
        return productService.getProduct(productId);
    }

    @Override
    public ProductResponse updateProduct(String productId, CreateProductRequest request) {
        return productService.updateProduct(productId, request);
    }

    @Override
    public List<ProductResponse> getProductsBySeller(String sellerId) {
        return productService.getProductsBySeller(sellerId);
    }
}
