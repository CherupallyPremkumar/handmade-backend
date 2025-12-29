package com.handmade.ecommerce.orchestration.product.dto;

import com.handmade.ecommerce.product.dto.CreateProductRequest;
import com.handmade.ecommerce.product.dto.CreateVariantCommand;
import com.handmade.ecommerce.pricing.command.CreatePriceCommand;
import com.handmade.ecommerce.inventory.command.CreateInventoryCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Fat DTO for coordinating the creation of a full Product offer.
 * This is the entry point for the Product Orchestrator.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrchestrationRequest {
    private CreateProductRequest productRequest;
    private List<VariantOrchestrationDetail> variants;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VariantOrchestrationDetail {
        private CreateVariantCommand variantCommand;
        private CreatePriceCommand priceCommand;
        private CreateInventoryCommand inventoryCommand;
    }
}
