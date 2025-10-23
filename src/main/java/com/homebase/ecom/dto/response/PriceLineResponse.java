package com.homebase.ecom.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO for price line with calculated fields
 */
public class PriceLineResponse {

    private String id;
    private String priceId;
    private String currency;
    private String region;
    private BigDecimal baseAmount;
    private BigDecimal saleAmount;
    private BigDecimal finalPrice;
    private BigDecimal discountPercentage;
    private BigDecimal savingsAmount;
    private BigDecimal savingsPercentage;
    private boolean onSale;
    private LocalDateTime saleStartDate;
    private LocalDateTime saleEndDate;
    private String priceType;
    private String currentState;
    private PriceTaxInfo taxInfo;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
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

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
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

    public LocalDateTime getSaleStartDate() {
        return saleStartDate;
    }

    public void setSaleStartDate(LocalDateTime saleStartDate) {
        this.saleStartDate = saleStartDate;
    }

    public LocalDateTime getSaleEndDate() {
        return saleEndDate;
    }

    public void setSaleEndDate(LocalDateTime saleEndDate) {
        this.saleEndDate = saleEndDate;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public PriceTaxInfo getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(PriceTaxInfo taxInfo) {
        this.taxInfo = taxInfo;
    }
}
