package com.handmade.ecommerce.pricing.service;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.entity.ExchangeRate;

/**
 * Currency Service Interface
 * Handles currency conversion and exchange rate management
 */
public interface CurrencyService {

    /**
     * Convert money from one currency to another
     * 
     * @param amount         Source amount with currency
     * @param targetCurrency Target currency code (e.g., "INR", "USD")
     * @return Converted money in target currency
     */
    Money convert(Money amount, String targetCurrency);

    /**
     * Get current exchange rate between two currencies
     * 
     * @param fromCurrency Source currency code
     * @param toCurrency   Target currency code
     * @return Exchange rate
     */
    ExchangeRate getExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Update exchange rates from external API
     * Should be called periodically via scheduled job
     */
    void updateExchangeRates();

    /**
     * Check if currency is supported
     * 
     * @param currencyCode Currency code to check
     * @return true if supported
     */
    boolean isCurrencySupported(String currencyCode);
}
