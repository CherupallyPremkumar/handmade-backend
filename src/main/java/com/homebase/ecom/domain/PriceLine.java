package com.homebase.ecom.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class PriceLine extends MultiTenantExtendedStateEntity{

    private String priceId;
    private String currency;         // USD, INR, etc.
    private String region;           // For regional pricing
    private BigDecimal amount;       // Base/original price
    private BigDecimal saleAmount;   // Explicit sale price (optional)
    private BigDecimal discountPercentage; // Optional if saleAmount not set
    private LocalDateTime saleStartDate;   // When sale begins
    private LocalDateTime saleEndDate;     // When sale ends
    private String priceType;        // e.g., "RETAIL", "WHOLESALE", "PROMOTIONAL"
    private String taxCategory;      // Optional: for linking with Tax Service
    // --- Derived logic ---
    public boolean isOnSale() {
        LocalDateTime now = LocalDateTime.now();

        boolean inDateRange = true;
        if (saleStartDate != null && saleEndDate != null) {
            inDateRange = now.isAfter(saleStartDate) && now.isBefore(saleEndDate);
        }

        boolean hasDiscount =
                (saleAmount != null && saleAmount.compareTo(amount) < 0)
                        || (discountPercentage != null && discountPercentage.compareTo(BigDecimal.ZERO) > 0);

        return inDateRange && hasDiscount && "ON_SALE".equals(getCurrentState().getStateId());
    }

    public BigDecimal getFinalPrice() {
        BigDecimal baseAmount = (amount != null) ? amount : BigDecimal.ZERO;

        if (isOnSale()) {
            if (saleAmount != null) return saleAmount;

            if (discountPercentage != null && discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal discountFactor = BigDecimal.ONE.subtract(
                        discountPercentage.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                );
                return baseAmount.multiply(discountFactor);
            }
        }

        return baseAmount;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
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

    public String getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }
}
