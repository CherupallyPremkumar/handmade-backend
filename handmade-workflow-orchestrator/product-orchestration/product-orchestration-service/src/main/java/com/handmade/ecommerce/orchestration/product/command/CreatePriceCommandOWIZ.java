package com.handmade.ecommerce.orchestration.product.command;

import com.handmade.ecommerce.orchestration.product.context.ProductOrchContext;
import com.handmade.ecommerce.orchestration.product.dto.ProductOrchestrationRequest;
import com.handmade.ecommerce.pricing.PricingService;
import com.handmade.ecommerce.pricing.command.CreatePriceCommand;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command to create prices for all variants in the request.
 */
@Component("create-price-command")
public class CreatePriceCommandOWIZ implements Command<ProductOrchContext> {

    private static final Logger logger = LoggerFactory.getLogger(CreatePriceCommandOWIZ.class);

    @Autowired
    private PricingService pricingService;

    @Override
    public void execute(ProductOrchContext context) {
        logger.info("Executing CreatePriceCommandOWIZ");

        if (context.getRequest().getVariants() == null) return;

        for (ProductOrchestrationRequest.VariantOrchestrationDetail detail : context.getRequest().getVariants()) {
            CreatePriceCommand priceCommand = detail.getPriceCommand();
            
            try {
                pricingService.createPrice(priceCommand);
                logger.info("Successfully created price for variant: {}", priceCommand.getVariantId());
            } catch (Exception e) {
                logger.error("Failed to create price for variant: {}", priceCommand.getVariantId(), e);
                throw new RuntimeException("Price creation failed", e);
            }
        }
    }
}
