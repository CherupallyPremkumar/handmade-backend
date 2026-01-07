package com.handmade.ecommerce.platform.domain.valueobject;

import java.util.Objects;

/**
 * CurrencyPair Value Object
 * Represents a currency exchange pair (e.g., USD/EUR)
 * Validates ISO 4217 currency codes
 */
public class CurrencyPair {
    private final String baseCurrency;
    private final String targetCurrency;

    public CurrencyPair(String baseCurrency, String targetCurrency) {
        validateCurrency(baseCurrency);
        validateCurrency(targetCurrency);

        if (baseCurrency.equals(targetCurrency)) {
            throw new IllegalArgumentException("Base and target currencies must be different");
        }

        this.baseCurrency = baseCurrency.toUpperCase();
        this.targetCurrency = targetCurrency.toUpperCase();
    }

    private void validateCurrency(String currency) {
        if (currency == null || currency.length() != 3) {
            throw new IllegalArgumentException("Currency code must be 3 characters (ISO 4217)");
        }
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    /**
     * Returns the inverse pair (e.g., EUR/USD from USD/EUR)
     */
    public CurrencyPair inverse() {
        return new CurrencyPair(targetCurrency, baseCurrency);
    }

    /**
     * Returns canonical string representation (e.g., "USD/EUR")
     */
    public String toPairString() {
        return baseCurrency + "/" + targetCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CurrencyPair))
            return false;
        CurrencyPair that = (CurrencyPair) o;
        return baseCurrency.equals(that.baseCurrency) &&
                targetCurrency.equals(that.targetCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurrency, targetCurrency);
    }

    @Override
    public String toString() {
        return toPairString();
    }
}
