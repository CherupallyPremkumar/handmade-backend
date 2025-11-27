package com.handmade.ecommerce.pricing.service;

import com.handmade.ecommerce.command.CreatePriceCommand;
import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.model.Price;

import java.math.Money;

/**
 * Pricing Service Interface
 * Handles product pricing, discounts, and price calculations
 */
public interface PricingService {

    Price createPrice(CreatePriceCommand command);
    


}
