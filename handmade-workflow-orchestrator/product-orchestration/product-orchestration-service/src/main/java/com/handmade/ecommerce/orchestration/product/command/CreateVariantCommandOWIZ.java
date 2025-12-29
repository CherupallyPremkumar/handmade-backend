package com.handmade.ecommerce.orchestration.product.command;

import com.handmade.ecommerce.orchestration.product.context.ProductOrchContext;
import com.handmade.ecommerce.orchestration.product.dto.ProductOrchestrationRequest;
import com.handmade.ecommerce.product.delegate.VariantManagerClient;
import com.handmade.ecommerce.product.dto.CreateVariantCommand;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command to create variants for the product.
 */
@Component("create-variant-command")
public class CreateVariantCommandOWIZ implements Command<ProductOrchContext> {

    private static final Logger logger = LoggerFactory.getLogger(CreateVariantCommandOWIZ.class);

    @Autowired
    private VariantManagerClient variantManagerClient;

    @Override
    public void execute(ProductOrchContext context) {
        logger.info("Executing CreateVariantCommand for Product ID: {}", context.getProductResponse().getId());

        for (ProductOrchestrationRequest.VariantOrchestrationDetail detail : context.getRequest().getVariants()) {
            CreateVariantCommand variantCommand = detail.getVariantCommand();
            // Ensure the variant is linked to the newly created product
            variantCommand.setProductId(context.getProductResponse().getId());
            
            try {
                variantManagerClient.createVariant(variantCommand);
                logger.info("Successfully created variant SKU: {}", variantCommand.getSku());
            } catch (Exception e) {
                logger.error("Failed to create variant SKU: {}", variantCommand.getSku(), e);
                throw new RuntimeException("Variant creation failed", e);
            }
        }
    }
}
