package com.handmade.ecommerce.pricing.service.cmds;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.dto.PricingExchange;
import com.handmade.ecommerce.pricing.entity.RegionalPrice;
import com.handmade.ecommerce.pricing.service.store.PriceEntityStore;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command: Step 2 - Check Regional Override
 * Checks if there's a regional price override for this variant
 */
@Component("checkRegionalOverrideCommand")
public class CheckRegionalOverrideCommand implements Command<PricingExchange> {

    private static final Logger logger = LoggerFactory.getLogger(CheckRegionalOverrideCommand.class);

    @Autowired
    private PriceEntityStore priceEntityStore;

    @Override
    public void execute(PricingExchange exchange) throws Exception {
        logger.info("Step 2: Checking regional override for variant: {} in region: {}",
                exchange.getVariantId(), exchange.getRegionCode());

        if (exchange.getRegionCode() == null) {
            logger.debug("No region code specified, skipping regional override check");
            return;
        }

        // TODO: Proper region code to ID mapping
        String region = exchange.getRegionCode();

        RegionalPrice regionalPrice = priceEntityStore.getRegionalPrice(
                exchange.getVariantId(), region);

        if (regionalPrice != null) {
            Money price = new Money(regionalPrice.getPriceAmount(), regionalPrice.getCurrency());
            exchange.setRegionalPrice(price);
            exchange.setHasRegionalOverride(true);
            logger.info("Regional override found: {} {}", price.getAmount(), price.getCurrency());
        } else {
            exchange.setHasRegionalOverride(false);
            logger.info("No regional override, using base price");
        }
    }
}
