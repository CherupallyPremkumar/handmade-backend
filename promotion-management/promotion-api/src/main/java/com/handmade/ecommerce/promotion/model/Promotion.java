package com.handmade.ecommerce.promotion.model;

import jakarta.persistence.*;
import org.chenile.utils.entity.model.ChenileEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Promotion - Aggregate Root
 * Represents a promotional campaign with discounts
 */
@Entity
@Table(name = "promotions", indexes = {
    @Index(name = "idx_code", columnList = "code"),
    @Index(name = "idx_active", columnList = "active"),
    @Index(name = "idx_target", columnList = "target_type, target_id")
})
public class Promotion extends ChenileEntity {
    
    @Id
    @Column(name = "promotion_id", length = 50)
    private String promotionId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name; // "Pottery Week Sale"
    
    @Column(name = "code", unique = true, length = 50)
    private String code; // "POTTERY20" - customer-facing promo code
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 20)
    private PromotionTargetType targetType; // CATEGORY, PRODUCT, COLLECTION, ARTISAN, GLOBAL
    
    @Column(name = "target_id", length = 50)
    private String targetId; // ID of category/product/collection/artisan
    
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false, length = 20)
    private DiscountType discountType; // PERCENTAGE, FIXED_AMOUNT, BUY_X_GET_Y
    
    @Column(name = "discount_value", precision = 10, scale = 2)
    private BigDecimal discountValue; // 20.00 for 20% or $20
    
    @Column(name = "max_discount_amount", precision = 10, scale = 2)
    private BigDecimal maxDiscountAmount; // Cap for percentage discounts
    
    @Column(name = "min_purchase_amount", precision = 10, scale = 2)
    private BigDecimal minPurchaseAmount; // Minimum order value to apply
    
    @Column(name = "start_date")
    private LocalDateTime startDate;
    
    @Column(name = "end_date")
    private LocalDateTime endDate;
    
    @Column(name = "active")
    private Boolean active = true;
    
    @Column(name = "stackable")
    private Boolean stackable = false; // Can be combined with other promotions
    
    @Column(name = "usage_limit")
    private Integer usageLimit; // Total usage limit
    
    @Column(name = "usage_count")
    private Integer usageCount = 0; // Current usage count
    
    @Column(name = "per_customer_limit")
    private Integer perCustomerLimit; // Usage limit per customer
    
    @Column(name = "priority")
    private Integer priority = 0; // Higher priority promotions applied first
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by", length = 50)
    private String createdBy;
    
    // For BUY_X_GET_Y promotions
    @Column(name = "buy_quantity")
    private Integer buyQuantity;
    
    @Column(name = "get_quantity")
    private Integer getQuantity;
    
    // Transient field for excluded product IDs
    @Transient
    private List<String> excludedProductIds = new ArrayList<>();
    
    // Getters and Setters
    
    public String getPromotionId() {
        return promotionId;
    }
    
    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public PromotionTargetType getTargetType() {
        return targetType;
    }
    
    public void setTargetType(PromotionTargetType targetType) {
        this.targetType = targetType;
    }
    
    public String getTargetId() {
        return targetId;
    }
    
    public void setTargetId(String targetId) {
        this.targetId = targetId;
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
    
    public BigDecimal getMaxDiscountAmount() {
        return maxDiscountAmount;
    }
    
    public void setMaxDiscountAmount(BigDecimal maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }
    
    public BigDecimal getMinPurchaseAmount() {
        return minPurchaseAmount;
    }
    
    public void setMinPurchaseAmount(BigDecimal minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Boolean getStackable() {
        return stackable;
    }
    
    public void setStackable(Boolean stackable) {
        this.stackable = stackable;
    }
    
    public Integer getUsageLimit() {
        return usageLimit;
    }
    
    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }
    
    public Integer getUsageCount() {
        return usageCount;
    }
    
    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }
    
    public Integer getPerCustomerLimit() {
        return perCustomerLimit;
    }
    
    public void setPerCustomerLimit(Integer perCustomerLimit) {
        this.perCustomerLimit = perCustomerLimit;
    }
    
    public Integer getPriority() {
        return priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Integer getBuyQuantity() {
        return buyQuantity;
    }
    
    public void setBuyQuantity(Integer buyQuantity) {
        this.buyQuantity = buyQuantity;
    }
    
    public Integer getGetQuantity() {
        return getQuantity;
    }
    
    public void setGetQuantity(Integer getQuantity) {
        this.getQuantity = getQuantity;
    }
    
    public List<String> getExcludedProductIds() {
        return excludedProductIds;
    }
    
    public void setExcludedProductIds(List<String> excludedProductIds) {
        this.excludedProductIds = excludedProductIds;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * Check if promotion is currently active based on date range and usage
     */
    public boolean isCurrentlyActive() {
        if (!active) {
            return false;
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (startDate != null && now.isBefore(startDate)) {
            return false;
        }
        if (endDate != null && now.isAfter(endDate)) {
            return false;
        }
        
        if (usageLimit != null && usageCount >= usageLimit) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Increment usage count
     */
    public void incrementUsage() {
        this.usageCount++;
    }
}
