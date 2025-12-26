package com.handmade.ecommerce.pricing.service.cmds;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.dto.PricingExchange;
import com.handmade.ecommerce.pricing.service.CurrencyService;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command: Step 3 - Convert Currency
 * Converts price to target currency if needed
 */
@Component("convertCurrencyCommand")
public class ConvertCurrencyCommand implements Command<PricingExchange> {

    private static final Logger logger = LoggerFactory.getLogger(ConvertCurrencyCommand.class);

    @Autowired(required = false)
    private CurrencyService currencyService;

    @Override
    public void execute(PricingExchange exchange) throws Exception {
        Money effectivePrice = exchange.getEffectivePrice();
        String targetCurrency = exchange.getTargetCurrency();

        logger.info("Step 3: Currency conversion check - current: {}, target: {}",
                effectivePrice.getCurrency(), targetCurrency);

        // If no target currency or same as current, skip
        if (targetCurrency == null || targetCurrency.equals(effectivePrice.getCurrency())) {
            exchange.setConvertedPrice(effectivePrice);
            exchange.setCurrencyConverted(false);
            logger.debug("No currency conversion needed");
            return;
        }

        // Convert currency
        if (currencyService != null) {
            Money convertedPrice = currencyService.convert(effectivePrice, targetCurrency);
            exchange.setConvertedPrice(convertedPrice);
            exchange.setCurrencyConverted(true);
            logger.info("Currency converted: {} {} -> {} {}",
                    effectivePrice.getAmount(), effectivePrice.getCurrency(),
                    convertedPrice.getAmount(), convertedPrice.getCurrency());
        } else {
            // Fallback: no conversion service available
            logger.warn("CurrencyService not available, skipping conversion");
            exchange.setConvertedPrice(effectivePrice);
            exchange.setCurrencyConverted(false);
        }
    }
}
