package com.handmade.ecommerce.promotion.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PromotionUsage - Entity
 * Tracks promotion usage by customers
 */
@Entity
@Table(name = "promotion_usage", indexes = {
    @Index(name = "idx_promotion_customer", columnList = "promotion_id, customer_id"),
    @Index(name = "idx_order", columnList = "order_id")
})
public class PromotionUsage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Long usageId;
    
    @Column(name = "promotion_id", nullable = false, length = 50)
    private String promotionId;
    
    @Column(name = "customer_id", nullable = false, length = 50)
    private String customerId;
    
    @Column(name = "order_id", nullable = false, length = 50)
    private String orderId;
    
    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount; // Actual discount applied
    
    @Column(name = "used_at")
    private LocalDateTime usedAt;
    
    // Getters and Setters
    
    public Long getUsageId() {
        return usageId;
    }
    
    public void setUsageId(Long usageId) {
        this.usageId = usageId;
    }
    
    public String getPromotionId() {
        return promotionId;
    }
    
    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
    
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
    
    public LocalDateTime getUsedAt() {
        return usedAt;
    }
    
    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }
    
    @PrePersist
    protected void onCreate() {
        usedAt = LocalDateTime.now();
    }
}
