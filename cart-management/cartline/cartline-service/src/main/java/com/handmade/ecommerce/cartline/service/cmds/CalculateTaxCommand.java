package com.handmade.ecommerce.cartline.service.cmds;

import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.tax.service.TaxService;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command: Calculate tax for cart line
 */
@Component("calculateTaxCommand")
public class CalculateTaxCommand implements Command<Cartline> {
    
    private static final Logger logger = LoggerFactory.getLogger(CalculateTaxCommand.class);
    
    @Autowired(required = false)
    private TaxService taxService;
    
    @Override
    public void execute(Cartline cartline) throws Exception {
        if (taxService == null) {
            logger.debug("TaxService not available, skipping tax calculation");
            return;
        }
        logger.info("Calculating tax for variant: {}", cartline.getVariantId());
        // Calculate tax on unit price
        Money taxAmount = taxService.calculateTax(cartline.getUnitPrice());
        
        // Store tax amount (can be used for display/reporting)
        cartline.setTaxAmount(taxAmount);
        // Note: Tax is typically calculated at cart level, not line level
        logger.info("Calculated tax for variant {}: {}", 
            cartline.getVariantId(), taxAmount);
    }
}
