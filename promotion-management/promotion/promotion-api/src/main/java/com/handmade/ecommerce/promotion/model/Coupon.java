package com.handmade.ecommerce.promotion.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Coupon - Aggregate Root for discount coupons
 */
@Entity
@Table(name = "coupons")
public class Coupon {
    
    @Id
    @Column(name = "coupon_id", length = 50)
    private String couponId;
    
    @Column(name = "code", length = 50, unique = true, nullable = false)
    private String code;
    
    @Column(name = "promotion_id", length = 50)
    private String promotionId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", length = 20)
    private DiscountType discountType;
    
    @Column(name = "discount_value", precision = 19, scale = 2)
    private BigDecimal discountValue;
    
    @Column(name = "minimum_order_value", precision = 19, scale = 2)
    private BigDecimal minimumOrderValue;
    
    @Column(name = "maximum_discount", precision = 19, scale = 2)
    private BigDecimal maximumDiscount;
    
    @Column(name = "max_usage_per_customer")
    private Integer maxUsagePerCustomer;
    
    @Column(name = "total_usage_limit")
    private Integer totalUsageLimit;
    
    @Column(name = "current_usage_count")
    private Integer currentUsageCount;
    
    @Column(name = "valid_from")
    private Instant validFrom;
    
    @Column(name = "valid_until")
    private Instant validUntil;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "created_at")
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    // Constructors
    public Coupon() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.isActive = true;
        this.currentUsageCount = 0;
    }
    
    // Business methods
    public boolean isValid() {
        Instant now = Instant.now();
        return isActive && 
               (validFrom == null || now.isAfter(validFrom)) &&
               (validUntil == null || now.isBefore(validUntil)) &&
               (totalUsageLimit == null || currentUsageCount < totalUsageLimit);
    }
    
    public void incrementUsage() {
        this.currentUsageCount++;
        this.updatedAt = Instant.now();
    }
    
    public BigDecimal calculateDiscount(BigDecimal orderAmount) {
        if (!isValid() || orderAmount.compareTo(minimumOrderValue) < 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal discount;
        if (discountType == DiscountType.PERCENTAGE) {
            discount = orderAmount.multiply(discountValue).divide(new BigDecimal("100"));
        } else {
            discount = discountValue;
        }
        
        if (maximumDiscount != null && discount.compareTo(maximumDiscount) > 0) {
            discount = maximumDiscount;
        }
        
        return discount;
    }
    
    // Getters and Setters
    public String getCouponId() { return couponId; }
    public void setCouponId(String couponId) { this.couponId = couponId; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getPromotionId() { return promotionId; }
    public void setPromotionId(String promotionId) { this.promotionId = promotionId; }
    
    public DiscountType getDiscountType() { return discountType; }
    public void setDiscountType(DiscountType discountType) { this.discountType = discountType; }
    
    public BigDecimal getDiscountValue() { return discountValue; }
    public void setDiscountValue(BigDecimal discountValue) { this.discountValue = discountValue; }
    
    public BigDecimal getMinimumOrderValue() { return minimumOrderValue; }
    public void setMinimumOrderValue(BigDecimal minimumOrderValue) { this.minimumOrderValue = minimumOrderValue; }
    
    public BigDecimal getMaximumDiscount() { return maximumDiscount; }
    public void setMaximumDiscount(BigDecimal maximumDiscount) { this.maximumDiscount = maximumDiscount; }
    
    public Integer getMaxUsagePerCustomer() { return maxUsagePerCustomer; }
    public void setMaxUsagePerCustomer(Integer maxUsagePerCustomer) { this.maxUsagePerCustomer = maxUsagePerCustomer; }
    
    public Integer getTotalUsageLimit() { return totalUsageLimit; }
    public void setTotalUsageLimit(Integer totalUsageLimit) { this.totalUsageLimit = totalUsageLimit; }
    
    public Integer getCurrentUsageCount() { return currentUsageCount; }
    public void setCurrentUsageCount(Integer currentUsageCount) { this.currentUsageCount = currentUsageCount; }
    
    public Instant getValidFrom() { return validFrom; }
    public void setValidFrom(Instant validFrom) { this.validFrom = validFrom; }
    
    public Instant getValidUntil() { return validUntil; }
    public void setValidUntil(Instant validUntil) { this.validUntil = validUntil; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public enum DiscountType {
        PERCENTAGE,
        FIXED_AMOUNT
    }
}
