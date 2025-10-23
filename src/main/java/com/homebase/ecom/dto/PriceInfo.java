package com.homebase.ecom.dto;

import java.math.BigDecimal;

/**
 * DTO containing calculated price information.
 * This separates pricing concerns from cart/order concerns.
 */
public class PriceInfo {

    private String priceId;
    private String priceLineId;
    private String currency;
    private String region;
    private BigDecimal baseAmount;
    private BigDecimal finalPrice;
    private BigDecimal savingsAmount;
    private BigDecimal savingsPercentage;
    private boolean onSale;
    private String priceType;

    public PriceInfo() {
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getPriceLineId() {
        return priceLineId;
    }

    public void setPriceLineId(String priceLineId) {
        this.priceLineId = priceLineId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public BigDecimal getSavingsAmount() {
        return savingsAmount;
    }

    public void setSavingsAmount(BigDecimal savingsAmount) {
        this.savingsAmount = savingsAmount;
    }

    public BigDecimal getSavingsPercentage() {
        return savingsPercentage;
    }

    public void setSavingsPercentage(BigDecimal savingsPercentage) {
        this.savingsPercentage = savingsPercentage;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }
}
