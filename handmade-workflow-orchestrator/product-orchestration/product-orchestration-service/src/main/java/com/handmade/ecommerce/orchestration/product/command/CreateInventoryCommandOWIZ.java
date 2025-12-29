package com.handmade.ecommerce.orchestration.product.command;

import com.handmade.ecommerce.inventory.command.CreateInventoryCommand;
import com.handmade.ecommerce.inventory.delegate.InventoryManagerClient;
import com.handmade.ecommerce.orchestration.product.context.ProductOrchContext;
import com.handmade.ecommerce.orchestration.product.dto.ProductOrchestrationRequest;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command to create inventory for all variants in the request.
 */
@Component("create-inventory-command")
public class CreateInventoryCommandOWIZ implements Command<ProductOrchContext> {

    private static final Logger logger = LoggerFactory.getLogger(CreateInventoryCommandOWIZ.class);

    @Autowired
    private InventoryManagerClient inventoryManagerClient;

    @Override
    public void execute(ProductOrchContext context) {
        logger.info("Executing CreateInventoryCommand");

        for (ProductOrchestrationRequest.VariantOrchestrationDetail detail : context.getRequest().getVariants()) {
            CreateInventoryCommand inventoryCommand = detail.getInventoryCommand();
            
            try {
                inventoryManagerClient.createInventory(inventoryCommand);
                logger.info("Successfully created inventory for variant: {}", inventoryCommand.getVariantId());
            } catch (Exception e) {
                logger.error("Failed to create inventory for variant: {}", inventoryCommand.getVariantId(), e);
                throw new RuntimeException("Inventory creation failed", e);
            }
        }
    }
}
