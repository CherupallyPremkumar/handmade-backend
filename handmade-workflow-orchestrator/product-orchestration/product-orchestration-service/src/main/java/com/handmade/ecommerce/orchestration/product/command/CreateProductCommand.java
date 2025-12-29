package com.handmade.ecommerce.orchestration.product.command;

import com.handmade.ecommerce.orchestration.product.context.ProductOrchContext;
import com.handmade.ecommerce.product.delegate.ProductManagerClient;
import com.handmade.ecommerce.product.dto.ProductResponse;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command to create a Product using ProductService.
 */
@Component("create-product-command")
public class CreateProductCommand implements Command<ProductOrchContext> {

    private static final Logger logger = LoggerFactory.getLogger(CreateProductCommand.class);

    @Autowired
    private ProductManagerClient productManagerClient;

    @Override
    public void execute(ProductOrchContext context) {
        logger.info("Executing CreateProductCommand for SKU: {}", context.getRequest().getProductRequest().getSku());

        try {
            ProductResponse productResponse = productManagerClient.createProduct(context.getRequest().getProductRequest());
            context.setProductResponse(productResponse);
            context.addResult("productId", productResponse.getId());
            
            logger.info("Successfully created product with ID: {}", productResponse.getId());
        } catch (Exception e) {
            logger.error("Failed to create product", e);
            throw new RuntimeException("Product creation failed", e);
        }
    }
}
