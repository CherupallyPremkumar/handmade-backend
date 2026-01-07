package com.handmade.ecommerce.platform.api;

import com.handmade.ecommerce.platform.domain.entity.ExchangeRate;
import com.handmade.ecommerce.platform.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Currency Exchange Service API
 * Provides currency conversion and exchange rate management
 */
public interface CurrencyExchangeService {

    /**
     * Convert money from one currency to another
     * 
     * @param amount     Money to convert
     * @param toCurrency Target currency code
     * @return Converted money
     * @throws ExchangeRateNotFoundException if no rate available
     */
    Money convert(Money amount, String toCurrency);

    /**
     * Get current exchange rate between two currencies
     * 
     * @param fromCurrency Base currency
     * @param toCurrency   Target currency
     * @return Current exchange rate
     * @throws ExchangeRateNotFoundException if no rate available
     */
    ExchangeRate getExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Get exchange rate valid at a specific time
     * 
     * @param fromCurrency Base currency
     * @param toCurrency   Target currency
     * @param asOf         Point in time for rate lookup
     * @return Exchange rate valid at specified time
     * @throws ExchangeRateNotFoundException if no rate available
     */
    ExchangeRate getExchangeRateAt(String fromCurrency, String toCurrency, LocalDateTime asOf);

    /**
     * Update or create a new exchange rate
     * 
     * @param baseCurrency   Base currency
     * @param targetCurrency Target currency
     * @param rate           Exchange rate value
     * @param source         Source of the rate (e.g., "MANUAL", "API")
     * @return Created exchange rate
     */
    ExchangeRate updateExchangeRate(String baseCurrency, String targetCurrency,
            BigDecimal rate, String source);

    /**
     * Exception thrown when exchange rate is not found
     */
    class ExchangeRateNotFoundException extends RuntimeException {
        public ExchangeRateNotFoundException(String message) {
            super(message);
        }
    }
}
