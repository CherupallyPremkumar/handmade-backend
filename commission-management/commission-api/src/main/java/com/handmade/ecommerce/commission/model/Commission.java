package com.handmade.ecommerce.commission.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Commission - Records the actual fee charged for a transaction
 * Immutable record for accounting and settlement
 */
@Entity
@Table(name = "commissions", indexes = {
    @Index(name = "idx_commission_order", columnList = "order_id"),
    @Index(name = "idx_commission_seller", columnList = "seller_id"),
    @Index(name = "idx_commission_status", columnList = "status")
})
public class Commission {
    
    @Id
    @Column(name = "commission_id", length = 50)
    private String commissionId;
    
    @Column(name = "order_id", length = 50, nullable = false)
    private String orderId;
    
    @Column(name = "order_item_id", length = 50)
    private String orderItemId; // If calculated per item
    
    @Column(name = "seller_id", length = 50, nullable = false)
    private String sellerId;
    
    @Column(name = "rule_applied_id", length = 50)
    private String ruleAppliedId; // Which rule was used
    
    @Column(name = "order_amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal orderAmount; // The base amount used for calculation
    
    @Column(name = "commission_amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal commissionAmount; // The fee charged
    
    @Column(name = "tax_amount", precision = 19, scale = 2)
    private BigDecimal taxAmount; // Tax on commission (GST/VAT)
    
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private CommissionStatus status;
    
    @Column(name = "settlement_id", length = 50)
    private String settlementId; // Links to Financial Settlement module
    
    @Column(name = "created_at")
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    // Constructors
    public Commission() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.status = CommissionStatus.PENDING;
        this.taxAmount = BigDecimal.ZERO;
    }
    
    // Business methods
    public BigDecimal getTotalDeduction() {
        return commissionAmount.add(taxAmount);
    }
    
    public void markAsCollected() {
        this.status = CommissionStatus.COLLECTED;
        this.updatedAt = Instant.now();
    }
    
    public void markAsSettled(String settlementId) {
        this.status = CommissionStatus.SETTLED;
        this.settlementId = settlementId;
        this.updatedAt = Instant.now();
    }
    
    public void refund() {
        this.status = CommissionStatus.REFUNDED;
        this.updatedAt = Instant.now();
    }
    
    // Getters and Setters
    public String getCommissionId() { return commissionId; }
    public void setCommissionId(String commissionId) { this.commissionId = commissionId; }
    
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public String getOrderItemId() { return orderItemId; }
    public void setOrderItemId(String orderItemId) { this.orderItemId = orderItemId; }
    
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    
    public String getRuleAppliedId() { return ruleAppliedId; }
    public void setRuleAppliedId(String ruleAppliedId) { this.ruleAppliedId = ruleAppliedId; }
    
    public BigDecimal getOrderAmount() { return orderAmount; }
    public void setOrderAmount(BigDecimal orderAmount) { this.orderAmount = orderAmount; }
    
    public BigDecimal getCommissionAmount() { return commissionAmount; }
    public void setCommissionAmount(BigDecimal commissionAmount) { this.commissionAmount = commissionAmount; }
    
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public CommissionStatus getStatus() { return status; }
    public void setStatus(CommissionStatus status) { this.status = status; }
    
    public String getSettlementId() { return settlementId; }
    public void setSettlementId(String settlementId) { this.settlementId = settlementId; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public enum CommissionStatus {
        PENDING,    // Calculated but not yet deducted
        COLLECTED,  // Deducted from seller balance
        SETTLED,    // Paid out to platform (internal accounting)
        REFUNDED,   // Returned to seller (e.g., order cancelled)
        CANCELLED   // Never collected
    }
}
