package com.handmade.ecommerce.dispute.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Dispute - Aggregate Root for Dispute Management
 * Handles conflicts between customers and sellers
 */
@Entity
@Table(name = "disputes", indexes = {
    @Index(name = "idx_dispute_order", columnList = "order_id"),
    @Index(name = "idx_dispute_customer", columnList = "customer_id"),
    @Index(name = "idx_dispute_seller", columnList = "seller_id"),
    @Index(name = "idx_dispute_status", columnList = "dispute_status")
})
public class Dispute {
    
    @Id
    @Column(name = "dispute_id", length = 50)
    private String disputeId;
    
    @Column(name = "order_id", length = 50, nullable = false)
    private String orderId;
    
    @Column(name = "customer_id", length = 50, nullable = false)
    private String customerId;
    
    @Column(name = "seller_id", length = 50, nullable = false)
    private String sellerId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "dispute_type", length = 30, nullable = false)
    private DisputeType disputeType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "dispute_status", length = 20, nullable = false)
    private DisputeStatus disputeStatus;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @OneToMany(mappedBy = "dispute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisputeEvidence> evidence = new ArrayList<>();
    
    @OneToMany(mappedBy = "dispute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisputeMessage> messages = new ArrayList<>();
    
    @Column(name = "seller_response", columnDefinition = "TEXT")
    private String sellerResponse;
    
    @Column(name = "seller_responded_at")
    private Instant sellerRespondedAt;
    
    @Column(name = "resolution", columnDefinition = "TEXT")
    private String resolution;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "resolution_type", length = 30)
    private ResolutionType resolutionType;
    
    @Column(name = "refund_amount", precision = 19, scale = 2)
    private BigDecimal refundAmount;
    
    @Column(name = "assigned_to", length = 50)
    private String assignedTo; // Admin/Support staff ID
    
    @Column(name = "priority", length = 10)
    @Enumerated(EnumType.STRING)
    private DisputePriority priority;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    @Column(name = "resolved_at")
    private Instant resolvedAt;
    
    @Column(name = "escalated_at")
    private Instant escalatedAt;
    
    // Constructors
    public Dispute() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.disputeStatus = DisputeStatus.OPEN;
        this.priority = DisputePriority.MEDIUM;
    }
    
    public Dispute(String disputeId, String orderId, String customerId, String sellerId, DisputeType disputeType) {
        this();
        this.disputeId = disputeId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.disputeType = disputeType;
    }
    
    // Business methods
    public void addEvidence(DisputeEvidence evidence) {
        this.evidence.add(evidence);
        evidence.setDispute(this);
        this.updatedAt = Instant.now();
    }
    
    public void addMessage(DisputeMessage message) {
        this.messages.add(message);
        message.setDispute(this);
        this.updatedAt = Instant.now();
    }
    
    public void addSellerResponse(String response) {
        this.sellerResponse = response;
        this.sellerRespondedAt = Instant.now();
        this.disputeStatus = DisputeStatus.UNDER_REVIEW;
        this.updatedAt = Instant.now();
    }
    
    public void escalate(String reason) {
        this.disputeStatus = DisputeStatus.ESCALATED;
        this.escalatedAt = Instant.now();
        this.priority = DisputePriority.HIGH;
        this.updatedAt = Instant.now();
        
        // Add system message
        DisputeMessage message = new DisputeMessage();
        message.setSenderType(SenderType.SYSTEM);
        message.setMessage("Dispute escalated: " + reason);
        addMessage(message);
    }
    
    public void resolve(ResolutionType resolutionType, String resolution, BigDecimal refundAmount) {
        this.resolutionType = resolutionType;
        this.resolution = resolution;
        this.refundAmount = refundAmount;
        this.disputeStatus = DisputeStatus.RESOLVED;
        this.resolvedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    public void close() {
        this.disputeStatus = DisputeStatus.CLOSED;
        this.updatedAt = Instant.now();
    }
    
    public boolean isOpen() {
        return DisputeStatus.OPEN.equals(this.disputeStatus) || 
               DisputeStatus.UNDER_REVIEW.equals(this.disputeStatus) ||
               DisputeStatus.ESCALATED.equals(this.disputeStatus);
    }
    
    public boolean requiresSellerResponse() {
        return DisputeStatus.OPEN.equals(this.disputeStatus) && sellerResponse == null;
    }
    
    public boolean isOverdue() {
        if (!isOpen()) return false;
        
        // Overdue if no seller response after 48 hours
        if (requiresSellerResponse()) {
            return Instant.now().isAfter(createdAt.plusSeconds(48 * 60 * 60));
        }
        
        // Overdue if under review for more than 7 days
        if (DisputeStatus.UNDER_REVIEW.equals(this.disputeStatus)) {
            return Instant.now().isAfter(createdAt.plusSeconds(7 * 24 * 60 * 60));
        }
        
        return false;
    }
    
    // Getters and Setters
    public String getDisputeId() { return disputeId; }
    public void setDisputeId(String disputeId) { this.disputeId = disputeId; }
    
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    
    public DisputeType getDisputeType() { return disputeType; }
    public void setDisputeType(DisputeType disputeType) { this.disputeType = disputeType; }
    
    public DisputeStatus getDisputeStatus() { return disputeStatus; }
    public void setDisputeStatus(DisputeStatus disputeStatus) { this.disputeStatus = disputeStatus; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public List<DisputeEvidence> getEvidence() { return evidence; }
    public void setEvidence(List<DisputeEvidence> evidence) { this.evidence = evidence; }
    
    public List<DisputeMessage> getMessages() { return messages; }
    public void setMessages(List<DisputeMessage> messages) { this.messages = messages; }
    
    public String getSellerResponse() { return sellerResponse; }
    public void setSellerResponse(String sellerResponse) { this.sellerResponse = sellerResponse; }
    
    public Instant getSellerRespondedAt() { return sellerRespondedAt; }
    public void setSellerRespondedAt(Instant sellerRespondedAt) { this.sellerRespondedAt = sellerRespondedAt; }
    
    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }
    
    public ResolutionType getResolutionType() { return resolutionType; }
    public void setResolutionType(ResolutionType resolutionType) { this.resolutionType = resolutionType; }
    
    public BigDecimal getRefundAmount() { return refundAmount; }
    public void setRefundAmount(BigDecimal refundAmount) { this.refundAmount = refundAmount; }
    
    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
    
    public DisputePriority getPriority() { return priority; }
    public void setPriority(DisputePriority priority) { this.priority = priority; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public Instant getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(Instant resolvedAt) { this.resolvedAt = resolvedAt; }
    
    public Instant getEscalatedAt() { return escalatedAt; }
    public void setEscalatedAt(Instant escalatedAt) { this.escalatedAt = escalatedAt; }
    
    /**
     * Dispute Type Enum
     */
    public enum DisputeType {
        PRODUCT_NOT_RECEIVED,
        PRODUCT_DAMAGED,
        WRONG_ITEM,
        NOT_AS_DESCRIBED,
        REFUND_NOT_RECEIVED,
        QUALITY_ISSUE,
        SELLER_UNRESPONSIVE,
        COUNTERFEIT_PRODUCT,
        LATE_DELIVERY,
        OTHER
    }
    
    /**
     * Dispute Status Enum
     */
    public enum DisputeStatus {
        OPEN,
        UNDER_REVIEW,
        ESCALATED,
        RESOLVED,
        CLOSED
    }
    
    /**
     * Resolution Type Enum
     */
    public enum ResolutionType {
        FULL_REFUND,
        PARTIAL_REFUND,
        REPLACEMENT,
        SELLER_FAVOR,
        MUTUAL_AGREEMENT,
        NO_ACTION
    }
    
    /**
     * Dispute Priority Enum
     */
    public enum DisputePriority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }
    
    /**
     * Sender Type Enum
     */
    public enum SenderType {
        CUSTOMER,
        SELLER,
        ADMIN,
        SYSTEM
    }
}
