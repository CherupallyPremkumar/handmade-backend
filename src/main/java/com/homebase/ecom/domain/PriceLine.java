package com.homebase.ecom.domain;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PriceLine domain model representing pricing information for a product variant.
 * Business logic has been moved to PriceCalculationService for better testability.
 */
public class PriceLine extends MultiTenantExtendedStateEntity{

    @NotBlank(message = "Price ID is required")
    private String priceId;
    
    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be 3-letter ISO code")
    private String currency;         // USD, INR, etc.
    
    private String region;           // For regional pricing
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;       // Base/original price
    
    @DecimalMin(value = "0.0", message = "Sale amount must be non-negative")
    private BigDecimal saleAmount;   // Explicit sale price (optional)
    
    @DecimalMin(value = "0.0", message = "Discount must be non-negative")
    @DecimalMax(value = "100.0", message = "Discount cannot exceed 100%")
    private BigDecimal discountPercentage; // Optional if saleAmount not set
    
    private LocalDateTime saleStartDate;   // When sale begins
    private LocalDateTime saleEndDate;     // When sale ends
    private String priceType;        // e.g., "RETAIL", "WHOLESALE", "PROMOTIONAL"
    private String taxCategory;      // Optional: for linking with Tax Service
    
    /**
     * @deprecated Use PriceCalculationService.isOnSale() instead
     * Business logic should not be in domain models
     */
    @Deprecated
    public boolean isOnSale() {
        // Kept for backward compatibility, but should use PriceCalculationService
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

    /**
     * @deprecated Use PriceCalculationService.calculateFinalPrice() instead
     * Business logic should not be in domain models
     */
    @Deprecated
    public BigDecimal getFinalPrice() {
        // Kept for backward compatibility, but should use PriceCalculationService
        BigDecimal baseAmount = (amount != null) ? amount : BigDecimal.ZERO;

        if (isOnSale()) {
            if (saleAmount != null) return saleAmount;

            if (discountPercentage != null && discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal discountFactor = BigDecimal.ONE.subtract(
                        discountPercentage.divide(BigDecimal.valueOf(100), 4, java.math.RoundingMode.HALF_UP)
                );
                return baseAmount.multiply(discountFactor);
            }
        }

        return baseAmount;
    }
    
    @AssertTrue(message = "Sale end date must be after start date")
    private boolean isValidSalePeriod() {
        if (saleStartDate == null || saleEndDate == null) {
            return true;
        }
        return saleEndDate.isAfter(saleStartDate);
    }
    
    @AssertTrue(message = "Sale amount must be less than base amount")
    private boolean isValidSaleAmount() {
        if (saleAmount == null || amount == null) {
            return true;
        }
        return saleAmount.compareTo(amount) < 0;
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
