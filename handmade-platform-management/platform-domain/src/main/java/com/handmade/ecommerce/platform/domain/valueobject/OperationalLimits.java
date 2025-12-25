package com.handmade.ecommerce.platform.domain.valueobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * OperationalLimits: Safety breakers to prevent technical or financial explosions.
 * Value Object - PURE DOMAIN MODEL.
 */
public class OperationalLimits implements Serializable {

    private BigDecimal maxTransactionAmount;
    private Integer maxSellersPerDay;
    private Integer globalRateLimit; // Requests per second

    public BigDecimal getMaxTransactionAmount() {
        return maxTransactionAmount;
    }

    public void setMaxTransactionAmount(BigDecimal maxTransactionAmount) {
        this.maxTransactionAmount = maxTransactionAmount;
    }

    public Integer getMaxSellersPerDay() {
        return maxSellersPerDay;
    }

    public void setMaxSellersPerDay(Integer maxSellersPerDay) {
        this.maxSellersPerDay = maxSellersPerDay;
    }

    public Integer getGlobalRateLimit() {
        return globalRateLimit;
    }

    public void setGlobalRateLimit(Integer globalRateLimit) {
        this.globalRateLimit = globalRateLimit;
    }
}
