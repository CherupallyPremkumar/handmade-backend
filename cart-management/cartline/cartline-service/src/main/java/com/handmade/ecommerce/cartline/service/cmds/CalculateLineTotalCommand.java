package com.handmade.ecommerce.cartline.service.cmds;

import com.handmade.ecommerce.cartline.model.Cartline;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command: Calculate final line total
 */
@Component("calculateLineTotalCommand")
public class CalculateLineTotalCommand implements Command<Cartline> {
    
    private static final Logger logger = LoggerFactory.getLogger(CalculateLineTotalCommand.class);
    
    @Override
    public void execute(Cartline cartline) throws Exception {
        logger.info("Calculating line total for variant: {}", cartline.getVariantId());
        
        // Calculate total: unitPrice * quantity
        // (discounts and tax are handled separately)
        cartline.calculateTotalPrice();
        
        logger.info("Calculated line total for variant {}: {}", 
            cartline.getVariantId(), cartline.getTotalPrice());
    }
}
