package com.handmade.ecommerce.coupon.model;

import com.handmade.ecommerce.core.model.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Coupon entity for discount codes
 */
@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CouponType type;

    @Column(name = "seller_id")
    private String sellerId; // null for platform coupons

    @NotNull
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @NotNull
    private BigDecimal discountValue; // 10 for 10% or 100 for ₹100

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "min_order_amount")),
        @AttributeOverride(name = "currencyCode", column = @Column(name = "min_order_currency"))
    })
    private Money minOrderValue;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "max_discount_amount")),
        @AttributeOverride(name = "currencyCode", column = @Column(name = "max_discount_currency"))
    })
    private Money maxDiscountAmount; // For percentage coupons

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime expiryDate;

    private Integer usageLimit; // null = unlimited

    @Column(nullable = false)
    private Integer usedCount = 0;

    @Column(nullable = false)
    private Boolean active = true;

    private String description;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public Money getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(Money minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public Money getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(Money maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Check if coupon is currently valid
     */
    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return active 
            && now.isAfter(startDate) 
            && now.isBefore(expiryDate)
            && (usageLimit == null || usedCount < usageLimit);
    }

    /**
     * Increment usage count
     */
    public void incrementUsage() {
        this.usedCount++;
    }
}
