package com.handmade.ecommerce.pricing.service.cmds;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.dto.PricingExchange;
import com.handmade.ecommerce.pricing.entity.Price;
import com.handmade.ecommerce.pricing.service.store.PriceEntityStore;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command: Step 1 - Get Base Price
 * Fetches the base price (USD) from database
 */
@Component("getBasePriceCommand")
public class GetBasePriceCommand implements Command<PricingExchange> {

    private static final Logger logger = LoggerFactory.getLogger(GetBasePriceCommand.class);

    @Autowired
    private PriceEntityStore priceEntityStore;

    @Override
    public void execute(PricingExchange exchange) throws Exception {
        logger.info("Step 1: Getting base price for variant: {}", exchange.getVariantId());

        Price price = priceEntityStore.getPriceByVariantId(exchange.getVariantId());

        Money basePrice = new Money(price.getCurrentPrice(), price.getBaseCurrency());
        exchange.setBasePrice(basePrice);
        exchange.setBaseCurrency(price.getBaseCurrency());

        logger.info("Base price fetched: {} {}", basePrice.getAmount(), basePrice.getCurrency());
    }
}
