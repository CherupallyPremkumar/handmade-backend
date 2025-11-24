package com.handmade.ecommerce.price.model;


import com.handmade.ecommerce.common.model.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class PriceLine extends AbstractJpaStateEntity {

    @NotBlank(message = "Price ID is required")
    private String priceId;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be 3-letter ISO code")
    private String currency;         // USD, INR, etc.

    private String region;           // For regional pricing

    @Embedded
    @NotNull(message = "Amount is required")
    private Money amount;       // Base/original price

    @Embedded
    private Money saleAmount;   // Optional sale price

    @DecimalMin(value = "0.0", message = "Discount must be non-negative")
    @DecimalMax(value = "100.0", message = "Discount cannot exceed 100%")
    private BigDecimal discountPercentage; // Optional if saleAmount not set

    private LocalDateTime saleStartDate;
    private LocalDateTime saleEndDate;

    private String priceType;        // e.g., "RETAIL", "WHOLESALE", "PROMOTIONAL"
    private String taxCategory;      // Optional: for linking with Tax Service


    @Column(name = "base_amount", precision = 19, scale = 4)
    private BigDecimal baseAmount;

    // ---------------- Business Logic ----------------

    /**
     * @deprecated Use PriceCalculationService.isOnSale() instead
     */
    @Deprecated
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

    /**
     * @deprecated Use PriceCalculationService.calculateFinalPrice() instead
     */
    @Deprecated
    public Money getFinalPrice() {
        Money base = amount != null ? amount : new Money(BigDecimal.ZERO, currency);

        if (isOnSale()) {
            if (saleAmount != null) return saleAmount;

            if (discountPercentage != null && discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal factor = BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_UP));
                return base.multiply(factor);
            }
        }

        return base;
    }

    @AssertTrue(message = "Sale end date must be after start date")
    private boolean isValidSalePeriod() {
        if (saleStartDate == null || saleEndDate == null) return true;
        return saleEndDate.isAfter(saleStartDate);
    }

    @AssertTrue(message = "Sale amount must be less than base amount")
    private boolean isValidSaleAmount() {
        if (saleAmount == null || amount == null) return true;
        return saleAmount.compareTo(amount) < 0;
    }

    // ---------------- Getters & Setters ----------------

    public String getPriceId() { return priceId; }
    public void setPriceId(String priceId) { this.priceId = priceId; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public Money getAmount() { return amount; }
    public void setAmount(Money amount) { this.amount = amount; }

    public Money getSaleAmount() { return saleAmount; }
    public void setSaleAmount(Money saleAmount) { this.saleAmount = saleAmount; }

    public BigDecimal getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(BigDecimal discountPercentage) { this.discountPercentage = discountPercentage; }

    public LocalDateTime getSaleStartDate() { return saleStartDate; }
    public void setSaleStartDate(LocalDateTime saleStartDate) { this.saleStartDate = saleStartDate; }

    public LocalDateTime getSaleEndDate() { return saleEndDate; }
    public void setSaleEndDate(LocalDateTime saleEndDate) { this.saleEndDate = saleEndDate; }

    public String getPriceType() { return priceType; }
    public void setPriceType(String priceType) { this.priceType = priceType; }

    public String getTaxCategory() { return taxCategory; }
    public void setTaxCategory(String taxCategory) { this.taxCategory = taxCategory; }
}