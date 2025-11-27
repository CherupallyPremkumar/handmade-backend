package com.handmade.ecommerce.productorch.service;

import com.handmade.ecommerce.command.CreatePriceCommand;
import com.handmade.ecommerce.command.CreateProductCommand;
import com.handmade.ecommerce.command.CreateVariantCommand;
import com.handmade.ecommerce.pricing.service.PricingService;
import org.chenile.owiz.Command;

/**
 * CreatePriceCommandService
 * Orchestration service that delegates price creation to PricingService
 * Runs in parallel with inventory creation
 */
public class CreatePriceCommandService implements Command<ProductContext> {
    
    private final PricingService pricingService;
    
    public CreatePriceCommandService(PricingService pricingService) {
        this.pricingService = pricingService;
    }
    
    @Override
    public void execute(ProductContext productContext) throws Exception {
        System.out.println("Creating prices for variants...");
        
        CreateProductCommand productCommand = productContext.getRequestProduct();
        
        if (productCommand == null || productCommand.getVariants() == null) {
            System.out.println("No variants found, skipping price creation");
            return;
        }
        
        // Process each variant's prices - delegate to PriceService
        for (CreateVariantCommand variant : productCommand.getVariants()) {
            if (variant.getPrices() != null && !variant.getPrices().isEmpty()) {
                for (CreatePriceCommand priceCommand : variant.getPrices()) {
                    // Just pass the command to PriceService
                    // PriceService handles: validation, calculation, persistence
                    pricingService.createPrice(priceCommand);
                }
            } else {
                System.out.println("Warning: Variant " + variant.getSku() + " has no prices defined");
            }
        }
        
        System.out.println("Price creation completed");
    }
}
