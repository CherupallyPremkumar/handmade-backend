package com.handmade.ecommerce.core.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * Immutable value object representing money (amount + currency)
 */

public final class Money {

    @NotNull
    private final BigDecimal amount;

    @NotNull
    private final Currency currency;

    // Scale for monetary calculations
    private static final int SCALE = 2;

    public Money(@NotNull BigDecimal amount, @NotNull String currencyCode) {
        if (amount == null) throw new IllegalArgumentException("Amount cannot be null");
        if (currencyCode == null) throw new IllegalArgumentException("Currency code cannot be null");

        this.amount = amount.setScale(SCALE, RoundingMode.HALF_UP);
        this.currency = Currency.getInstance(currencyCode.toUpperCase());
    }

    public Money(@NotNull BigDecimal amount, @NotNull Currency currency) {
        if (amount == null || currency == null)
            throw new IllegalArgumentException("Amount and Currency cannot be null");

        this.amount = amount.setScale(SCALE, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    /** Addition of two Money objects in the same currency */
    public Money add(Money other) {
        ensureSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    /** Subtraction of two Money objects in the same currency */
    public Money subtract(Money other) {
        ensureSameCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    /** Multiply by a scalar */
    public Money multiply(BigDecimal factor) {
        return new Money(this.amount.multiply(factor), this.currency);
    }

    /** Divide by a scalar */
    public Money divide(BigDecimal divisor) {
        return new Money(this.amount.divide(divisor, SCALE, RoundingMode.HALF_UP), this.currency);
    }

    /** Compare two Money objects in the same currency */
    public int compareTo(Money other) {
        ensureSameCurrency(other);
        return this.amount.compareTo(other.amount);
    }

    /** Check equality including currency */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.equals(money.amount) && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return currency.getCurrencyCode() + " " + amount.toPlainString();
    }

    /** Ensure the currency matches for operations */
    private void ensureSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                    "Currency mismatch: " + this.currency + " vs " + other.currency);
        }
    }
}