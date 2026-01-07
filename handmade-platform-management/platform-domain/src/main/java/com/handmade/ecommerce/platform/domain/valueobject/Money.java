package com.handmade.ecommerce.platform.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Money Value Object - Represents an amount with currency
 * Immutable and type-safe to prevent currency mixing bugs
 * 
 * Used across all modules: pricing, finance, orders, commission
 */
public class Money {
    private final BigDecimal amount;
    private final String currency;

    private static final int SCALE = 4;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    public Money(BigDecimal amount, String currency) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (currency == null || currency.length() != 3) {
            throw new IllegalArgumentException("Currency must be 3-letter ISO 4217 code");
        }
        this.amount = amount.setScale(SCALE, ROUNDING);
        this.currency = currency.toUpperCase();
    }

    public Money(double amount, String currency) {
        this(BigDecimal.valueOf(amount), currency);
    }

    public Money(String amount, String currency) {
        this(new BigDecimal(amount), currency);
    }

    /**
     * Add two money amounts (must be same currency)
     */
    public Money add(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    /**
     * Subtract two money amounts (must be same currency)
     */
    public Money subtract(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    /**
     * Multiply by a factor
     */
    public Money multiply(BigDecimal multiplier) {
        return new Money(this.amount.multiply(multiplier), this.currency);
    }

    /**
     * Divide by a divisor
     */
    public Money divide(BigDecimal divisor) {
        return new Money(this.amount.divide(divisor, SCALE, ROUNDING), this.currency);
    }

    /**
     * Check if this money is greater than another
     */
    public boolean isGreaterThan(Money other) {
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount) > 0;
    }

    /**
     * Check if this money is less than another
     */
    public boolean isLessThan(Money other) {
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount) < 0;
    }

    /**
     * Check if amount is zero
     */
    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Check if amount is positive
     */
    public boolean isPositive() {
        return this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    private void validateSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new CurrencyMismatchException(
                    "Cannot operate on different currencies: " +
                            this.currency + " and " + other.currency);
        }
    }

    // Getters
    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Money))
            return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0 && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return amount.toPlainString() + " " + currency;
    }

    /**
     * Exception thrown when trying to operate on different currencies
     */
    public static class CurrencyMismatchException extends RuntimeException {
        public CurrencyMismatchException(String message) {
            super(message);
        }
    }
}
