package com.homebase.ecom.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request DTO for creating a new price line
 */
public class CreatePriceLineRequest {

    @NotBlank(message = "Price ID is required")
    private String priceId;

    @NotNull(message = "Base amount is required")
    @DecimalMin(value = "0.01", message = "Base amount must be greater than 0")
    private BigDecimal baseAmount;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be 3-letter ISO code")
    private String currency;

    @NotBlank(message = "Region is required")
    private String region;

    @DecimalMin(value = "0.0", message = "Sale amount must be non-negative")
    private BigDecimal saleAmount;

    @DecimalMin(value = "0.0", message = "Discount must be non-negative")
    @DecimalMax(value = "100.0", message = "Discount cannot exceed 100%")
    private BigDecimal discountPercentage;

    private LocalDateTime saleStartDate;
    private LocalDateTime saleEndDate;

    private String priceType;
    private String taxCategory;

    // Getters and Setters
    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
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

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
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

    @AssertTrue(message = "Sale end date must be after start date")
    public boolean isValidSalePeriod() {
        if (saleStartDate == null || saleEndDate == null) {
            return true;
        }
        return saleEndDate.isAfter(saleStartDate);
    }

    @AssertTrue(message = "Sale amount must be less than base amount")
    public boolean isValidSaleAmount() {
        if (saleAmount == null || baseAmount == null) {
            return true;
        }
        return saleAmount.compareTo(baseAmount) < 0;
    }
}
