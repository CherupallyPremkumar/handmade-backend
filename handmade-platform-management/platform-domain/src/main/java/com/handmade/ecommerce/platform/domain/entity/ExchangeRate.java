package com.handmade.ecommerce.platform.domain.entity;

import org.chenile.jpautils.entity.BaseJpaEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ExchangeRate Entity
 * Stores currency exchange rates with temporal validity
 * Immutable once created - new rates are created for updates
 */
@Entity
@Table(name = "hm_platform_exchange_rates", indexes = {
        @Index(name = "idx_currency_pair_date", columnList = "base_currency, target_currency, effective_from, effective_to")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = { "base_currency", "target_currency", "effective_from" })
})
public class ExchangeRate extends BaseJpaEntity {

    @Column(name = "base_currency", length = 3, nullable = false)
    private String baseCurrency;

    @Column(name = "target_currency", length = 3, nullable = false)
    private String targetCurrency;

    /**
     * Exchange rate with 6 decimal precision
     * e.g., 1 USD = 0.850000 EUR means rate = 0.850000
     */
    @Column(name = "rate", precision = 19, scale = 6, nullable = false)
    private BigDecimal rate;

    @Column(name = "effective_from", nullable = false)
    private LocalDateTime effectiveFrom;

    @Column(name = "effective_to")
    private LocalDateTime effectiveTo;

    /**
     * Source of the rate (e.g., "MANUAL", "OPEN_EXCHANGE_RATES", "FIXER_IO")
     */
    @Column(name = "source", length = 50)
    private String source = "MANUAL";

    /**
     * Check if this rate is valid at the given time
     */
    public boolean isValidAt(LocalDateTime dateTime) {
        boolean afterStart = !dateTime.isBefore(effectiveFrom);
        boolean beforeEnd = effectiveTo == null || !dateTime.isAfter(effectiveTo);
        return afterStart && beforeEnd;
    }

    /**
     * Calculate inverse rate (e.g., EUR/USD from USD/EUR)
     */
    public BigDecimal getInverseRate() {
        return BigDecimal.ONE.divide(rate, 6, java.math.RoundingMode.HALF_UP);
    }

    // Getters and Setters
    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDateTime getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDateTime effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDateTime getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDateTime effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
