package com.handmade.ecommerce.cartline.service.cmds;

import com.handmade.ecommerce.cartline.model.Cartline;
import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.PricingService;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command: Fetch current price for variant
 */
@Component("fetchPriceCommand")
public class FetchPriceCommand implements Command<Cartline> {
    
    private static final Logger logger = LoggerFactory.getLogger(FetchPriceCommand.class);
    
    @Autowired
    private PricingService pricingService;
    
    @Override
    public void execute(Cartline cartline) throws Exception {
        logger.info("Fetching price for variant: {}", cartline.getVariantId());

        Money pricing = pricingService.getCurrentPrice(cartline.getVariantId());
        cartline.setUnitPrice(pricing);

        logger.info("Fetched price for variant {}: {}", 
            cartline.getVariantId(), pricing);
    }
}
