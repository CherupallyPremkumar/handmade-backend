package com.handmade.ecommerce.price.service.impl;

import com.handmade.ecommerce.command.CreatePriceCommand;
import com.handmade.ecommerce.command.CreatePriceRuleCommand;
import com.handmade.ecommerce.command.CreateRegionalPriceCommand;
import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.model.Price;
import com.handmade.ecommerce.pricing.model.PriceRule;
import com.handmade.ecommerce.pricing.model.RegionalPrice;
import com.handmade.ecommerce.pricing.service.PricingService;
import com.handmade.ecommerce.price.service.store.PriceEntityStore;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Scalable Amazon-style pricing service implementation
 * 
 * Design Patterns:
 * - Stream API for batch processing (scalable)
 * - Functional programming for immutability
 * - Separation of concerns (mapping methods)
 */
public class PriceServiceImpl extends StateEntityServiceImpl<Price> implements PricingService {

    private static final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);
    private final PriceEntityStore priceEntityStore;

    public PriceServiceImpl(STM<Price> stm, STMActionsInfoProvider stmActionsInfoProvider,
            PriceEntityStore entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
        this.priceEntityStore = entityStore;
    }

    @Override
    public Price createPrice(CreatePriceCommand command) {
        logger.info("Creating scalable price for variant: {}", command.getVariantId());

        Price price = new Price();

        // 1. Map base pricing
        mapBasePricing(price, command);

        // 2. Add regional prices (scalable batch processing)
        addRegionalPrices(price, command.getRegionalPrices());

        // 3. Add price rules (scalable batch processing)
        addPriceRules(price, command.getPriceRules());

        // 4. Persist using state machine
        super.create(price);

        logger.info("✅ Price created with {} regional prices and {} rules",
                price.getRegionalPrices().size(),
                price.getPriceRules().size());

        return price;
    }

    /**
     * Map base pricing fields
     * Scalability: Centralized mapping logic
     */
    private void mapBasePricing(Price price, CreatePriceCommand command) {
        price.setVariantId(command.getVariantId());
        price.setListPrice(command.getListPrice());
        price.setCurrentPrice(command.getCurrentPrice());
        price.setBaseCurrency(command.getCurrency() != null ? command.getCurrency() : "USD");

        // Auto-calculate discount
        price.calculateDiscount();

        // Set special pricing
        if (command.getPrimePrice() != null) {
            price.setPrimePrice(command.getPrimePrice());
        }

        if (command.getSubscribeSavePercent() != null) {
            price.setSubscribeSavePercent(command.getSubscribeSavePercent());
        }

        // Calculate Subscribe & Save price
        price.calculateSubscribeSavePrice();

        // Set effective dates
        price.setEffectiveFrom(command.getEffectiveFrom() != null ? command.getEffectiveFrom() : LocalDateTime.now());
        price.setEffectiveTo(command.getEffectiveTo());
    }

    /**
     * Add regional prices using Stream API
     * Scalability: Handles unlimited regions efficiently
     * Performance: O(n) complexity, parallelizable
     */
    private void addRegionalPrices(Price price, List<CreateRegionalPriceCommand> commands) {
        if (commands == null || commands.isEmpty()) {
            logger.debug("No regional prices to add");
            return;
        }

        logger.info("Adding {} regional prices", commands.size());

        commands.stream()
                .map(this::mapToRegionalPrice)
                .forEach(price::addRegionalPrice);
    }

    /**
     * Map command to RegionalPrice entity
     * Scalability: Reusable mapping logic
     */
    private RegionalPrice mapToRegionalPrice(CreateRegionalPriceCommand command) {
        RegionalPrice regional = new RegionalPrice();
        regional.setRegion(command.getRegion());
        regional.setCurrency(command.getCurrency());
        regional.setPriceAmount(command.getPriceAmount());
        regional.setTaxRate(command.getTaxRate());
        regional.setIncludesTax(command.getIncludesTax() != null ? command.getIncludesTax() : false);
        regional.setEffectiveFrom(command.getEffectiveFrom());
        regional.setEffectiveTo(command.getEffectiveTo());
        regional.setIsActive(true);

        logger.debug("Mapped regional price: {} {} {}",
                command.getRegion(), command.getCurrency(), command.getPriceAmount());

        return regional;
    }

    /**
     * Add price rules using Stream API
     * Scalability: Handles unlimited rules efficiently
     * Performance: O(n) complexity, parallelizable
     */
    private void addPriceRules(Price price, List<CreatePriceRuleCommand> commands) {
        if (commands == null || commands.isEmpty()) {
            logger.debug("No price rules to add");
            return;
        }

        logger.info("Adding {} price rules", commands.size());

        commands.stream()
                .map(this::mapToPriceRule)
                .forEach(price::addPriceRule);
    }

    /**
     * Map command to PriceRule entity
     * Scalability: Reusable mapping logic
     */
    private PriceRule mapToPriceRule(CreatePriceRuleCommand command) {
        PriceRule rule = new PriceRule();
        rule.setRuleType(command.getRuleType());
        rule.setRuleCondition(command.getRuleCondition());
        rule.setAdjustmentType(command.getAdjustmentType());
        rule.setAdjustmentValue(command.getAdjustmentValue());
        rule.setPriority(command.getPriority() != null ? command.getPriority() : 0);
        rule.setValidFrom(command.getValidFrom());
        rule.setValidUntil(command.getValidUntil());
        rule.setIsActive(true);

        logger.debug("Mapped price rule: {} - {} {}",
                command.getRuleType(), command.getAdjustmentType(), command.getAdjustmentValue());

        return rule;
    }
}
