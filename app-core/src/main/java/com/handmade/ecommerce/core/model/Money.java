package com.handmade.ecommerce.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

@Embeddable
public class Money {

    @NotNull
    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(name = "currency", length = 3)
    private String currencyCode;

    private static final int SCALE = 2;

    protected Money() {
        // Required by JPA, DO NOT REMOVE
    }

    public Money(BigDecimal amount, String currencyCode) {
        if (amount == null) throw new IllegalArgumentException("Amount cannot be null");
        if (currencyCode == null) throw new IllegalArgumentException("Currency code cannot be null");

        this.amount = amount.setScale(SCALE, RoundingMode.HALF_UP);
        this.currencyCode = currencyCode.toUpperCase();
    }

    public Money(BigDecimal amount, Currency currency) {
        this(amount, currency.getCurrencyCode());
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return Currency.getInstance(currencyCode);
    }

    public Money add(Money other) {
        ensureSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currencyCode);
    }

    public Money subtract(Money other) {
        ensureSameCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currencyCode);
    }

    public Money multiply(BigDecimal factor) {
        return new Money(this.amount.multiply(factor), this.currencyCode);
    }

    public Money divide(BigDecimal divisor) {
        return new Money(this.amount.divide(divisor, SCALE, RoundingMode.HALF_UP), this.currencyCode);
    }

    private void ensureSameCurrency(Money other) {
        if (!this.currencyCode.equals(other.currencyCode)) {
            throw new IllegalArgumentException("Currency mismatch: " + this.currencyCode + " vs " + other.currencyCode);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.equals(money.amount) &&
                currencyCode.equals(money.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currencyCode);
    }

    @Override
    public String toString() {
        return currencyCode + " " + amount.toPlainString();
    }
}