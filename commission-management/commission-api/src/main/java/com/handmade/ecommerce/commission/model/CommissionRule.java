package com.handmade.ecommerce.commission.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * CommissionRule - Defines how commissions are calculated
 * Can be global, category-specific, or seller-specific
 */
@Entity
@Table(name = "commission_rules", indexes = {
    @Index(name = "idx_rule_category", columnList = "category_id"),
    @Index(name = "idx_rule_seller", columnList = "seller_id"),
    @Index(name = "idx_rule_active", columnList = "is_active")
})
public class CommissionRule {
    
    @Id
    @Column(name = "rule_id", length = 50)
    private String ruleId;
    
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    
    @Column(name = "description", length = 255)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private CommissionType type;
    
    @Column(name = "value", precision = 19, scale = 4, nullable = false)
    private BigDecimal value; // Percentage (e.g., 15.0) or Fixed Amount (e.g., 1.00)
    
    @Column(name = "category_id", length = 50)
    private String categoryId; // Null means applies to all categories
    
    @Column(name = "seller_id", length = 50)
    private String sellerId; // Null means applies to all sellers
    
    @Column(name = "min_commission", precision = 19, scale = 2)
    private BigDecimal minCommission; // Minimum fee (e.g., $0.50)
    
    @Column(name = "max_commission", precision = 19, scale = 2)
    private BigDecimal maxCommission; // Cap (e.g., max $50.00)
    
    @Column(name = "priority")
    private Integer priority; // Higher priority rules override lower ones
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "start_date")
    private Instant startDate;
    
    @Column(name = "end_date")
    private Instant endDate;
    
    @Column(name = "created_at")
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    // Constructors
    public CommissionRule() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.isActive = true;
        this.priority = 0;
    }
    
    // Business methods
    public boolean isApplicable(String categoryId, String sellerId) {
        if (!Boolean.TRUE.equals(isActive)) return false;
        
        Instant now = Instant.now();
        if (startDate != null && now.isBefore(startDate)) return false;
        if (endDate != null && now.isAfter(endDate)) return false;
        
        // Check category match (exact match or rule is global)
        boolean categoryMatch = this.categoryId == null || this.categoryId.equals(categoryId);
        
        // Check seller match (exact match or rule is global)
        boolean sellerMatch = this.sellerId == null || this.sellerId.equals(sellerId);
        
        return categoryMatch && sellerMatch;
    }
    
    public BigDecimal calculateCommission(BigDecimal orderAmount) {
        BigDecimal calculated = BigDecimal.ZERO;
        
        if (CommissionType.PERCENTAGE.equals(type)) {
            // value is percentage (e.g., 15.0 for 15%)
            calculated = orderAmount.multiply(value).divide(new BigDecimal("100"));
        } else if (CommissionType.FIXED.equals(type)) {
            // value is fixed amount
            calculated = value;
        }
        
        // Apply min/max constraints
        if (minCommission != null && calculated.compareTo(minCommission) < 0) {
            calculated = minCommission;
        }
        
        if (maxCommission != null && calculated.compareTo(maxCommission) > 0) {
            calculated = maxCommission;
        }
        
        return calculated;
    }
    
    // Getters and Setters
    public String getRuleId() { return ruleId; }
    public void setRuleId(String ruleId) { this.ruleId = ruleId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public CommissionType getType() { return type; }
    public void setType(CommissionType type) { this.type = type; }
    
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
    
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    
    public BigDecimal getMinCommission() { return minCommission; }
    public void setMinCommission(BigDecimal minCommission) { this.minCommission = minCommission; }
    
    public BigDecimal getMaxCommission() { return maxCommission; }
    public void setMaxCommission(BigDecimal maxCommission) { this.maxCommission = maxCommission; }
    
    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public Instant getStartDate() { return startDate; }
    public void setStartDate(Instant startDate) { this.startDate = startDate; }
    
    public Instant getEndDate() { return endDate; }
    public void setEndDate(Instant endDate) { this.endDate = endDate; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public enum CommissionType {
        PERCENTAGE,
        FIXED,
        HYBRID // e.g., 5% + $0.50 (requires more complex logic)
    }
}
