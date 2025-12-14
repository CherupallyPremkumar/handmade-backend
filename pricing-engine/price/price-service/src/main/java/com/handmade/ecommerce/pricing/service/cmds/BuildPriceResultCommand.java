package com.handmade.ecommerce.pricing.service.cmds;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.dto.PriceCalculationResult;
import com.handmade.ecommerce.pricing.dto.PricingExchange;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command: Step 6 - Build Price Result
 * Assembles the final PriceCalculationResult
 */
@Component("buildPriceResultCommand")
public class BuildPriceResultCommand implements Command<PricingExchange> {

    private static final Logger logger = LoggerFactory.getLogger(BuildPriceResultCommand.class);

    @Override
    public void execute(PricingExchange exchange) throws Exception {
        logger.info("Step 6: Building price result");

        Money discountedPrice = exchange.getDiscountedPrice();
        Money taxAmount = exchange.getTaxAmount();

        // Calculate total
        Money total = new Money(
                discountedPrice.getAmount().add(taxAmount.getAmount()),
                discountedPrice.getCurrency());
        exchange.setTotal(total);

        // Build result
        PriceCalculationResult result = PriceCalculationResult.builder()
                .basePrice(exchange.getBasePrice())
                .convertedPrice(exchange.getConvertedPrice())
                .appliedRules(exchange.getAppliedRules())
                .discount(exchange.getTotalDiscount())
                .subtotal(discountedPrice)
                .tax(taxAmount)
                .total(total)
                .currency(discountedPrice.getCurrency())
                .taxDescription(exchange.getTaxDescription())
                .build();

        exchange.setResult(result);

        logger.info("✅ Price result built: base={}, discount={}, subtotal={}, tax={}, total={}",
                exchange.getBasePrice(),
                exchange.getTotalDiscount(),
                discountedPrice,
                taxAmount,
                total);
    }
}
