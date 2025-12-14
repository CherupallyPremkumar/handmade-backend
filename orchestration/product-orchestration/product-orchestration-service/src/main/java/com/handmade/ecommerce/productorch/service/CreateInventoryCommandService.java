package com.handmade.ecommerce.productorch.service;

import com.handmade.ecommerce.command.CreateInventoryCommand;
import com.handmade.ecommerce.command.CreateVariantCommand;
import com.handmade.ecommerce.inventory.service.InventoryService;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateInventoryCommandService implements Command<ProductContext> {

    @Autowired
    private InventoryService inventoryService;

    @Override
    public void execute(ProductContext context) throws Exception {
        List<CreateVariantCommand> createVariantCommands = context.getRequestProduct().getVariants();

        // Iterate through each variant and create inventory using the embedded inventory command
        if (createVariantCommands != null && !createVariantCommands.isEmpty()) {
            for (CreateVariantCommand variantCommand : createVariantCommands) {
                // Get the inventory command from the variant
                CreateInventoryCommand inventoryCommand = variantCommand.getInventory();

                if (inventoryCommand != null) {
                    // Call inventory service with the command from variant
                    inventoryService.createInventory(inventoryCommand);
                }
            }
        }
    }
}