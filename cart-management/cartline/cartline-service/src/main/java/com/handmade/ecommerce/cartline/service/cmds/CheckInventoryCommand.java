package com.handmade.ecommerce.cartline.service.cmds;

import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.inventory.service.InventoryService;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command: Check inventory availability (soft check)
 */
@Component("checkInventoryCommand")
public class CheckInventoryCommand implements Command<Cartline> {
    
    private static final Logger logger = LoggerFactory.getLogger(CheckInventoryCommand.class);
    
    @Autowired(required = false)
    private InventoryService inventoryService;
    
    @Override
    public void execute(Cartline cartline) throws Exception {
        if (inventoryService == null) {
            logger.debug("InventoryService not available, skipping inventory check");
            return;
        }
        logger.info("Checking inventory for variant: {}", cartline.getVariantId());
        
        try {
            Integer availableStock = inventoryService.getAvailableStock(
                cartline.getVariantId()
            );
            
            if (availableStock != null && availableStock < cartline.getQuantity()) {
                logger.warn("Low stock for variant {}: requested={}, available={}", 
                    cartline.getVariantId(), cartline.getQuantity(), availableStock);
                
                // Soft check - warn but don't fail
                // Hard check will be done at checkout
            } else {
                logger.info("Inventory check passed for variant {}", cartline.getVariantId());
            }
        } catch (Exception e) {
            logger.error("Inventory check failed for variant: {}", cartline.getVariantId(), e);
            // Don't fail - inventory check is non-critical at add-to-cart
        }
    }
}
