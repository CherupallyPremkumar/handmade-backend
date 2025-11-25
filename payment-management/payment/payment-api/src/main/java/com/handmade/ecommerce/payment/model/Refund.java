package com.handmade.ecommerce.payment.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Refund - Aggregate Root for payment refunds
 */
@Entity
@Table(name = "refunds")
public class Refund {

    @Id
    @Column(name = "refund_id", length = 50)
    private String refundId;

    @Column(name = "payment_transaction_id", length = 50, nullable = false)
    private String paymentTransactionId;

    @Column(name = "order_id", length = 50, nullable = false)
    private String orderId;

    @Column(name = "amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", length = 3)
    private String currency;

    @Column(name = "reason", length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "refund_status", length = 20)
    private RefundStatus refundStatus;

    @Column(name = "gateway_refund_id", length = 100)
    private String gatewayRefundId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "processed_at")
    private Instant processedAt;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    // Constructors
    public Refund() {
        this.createdAt = Instant.now();
        this.refundStatus = RefundStatus.PENDING;
    }

    public Refund(String refundId, String paymentTransactionId, String orderId, BigDecimal amount, String currency) {
        this();
        this.refundId = refundId;
        this.paymentTransactionId = paymentTransactionId;
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
    }

    // Business methods
    public void markAsProcessed(String gatewayRefundId) {
        this.refundStatus = RefundStatus.PROCESSED;
        this.gatewayRefundId = gatewayRefundId;
        this.processedAt = Instant.now();
    }

    public void markAsFailed(String errorMessage) {
        this.refundStatus = RefundStatus.FAILED;
        this.errorMessage = errorMessage;
    }

    // Getters and Setters
    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RefundStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getGatewayRefundId() {
        return gatewayRefundId;
    }

    public void setGatewayRefundId(String gatewayRefundId) {
        this.gatewayRefundId = gatewayRefundId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public enum RefundStatus {
        PENDING,
        PROCESSED,
        FAILED
    }
}
