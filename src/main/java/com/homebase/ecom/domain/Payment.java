package com.homebase.ecom.domain;

import org.chenile.utils.entity.model.AbstractExtendedStateEntity;

import java.math.BigDecimal;

public class Payment extends MultiTenantExtendedStateEntity {

    private Order order;
    private String transactionId;
    private String merchantTransactionId;
    private BigDecimal amount;
    private String currency = "INR";
    private PaymentStatus status = PaymentStatus.PENDING;
    private PaymentMethod method;
    private PaymentGateway gateway;
    private String gatewayResponse;
    private String failureReason;

    public enum PaymentStatus {
        PENDING,
        PROCESSING,
        SUCCESS,
        FAILED,
        REFUNDED,
        CANCELLED
    }

    public enum PaymentMethod {
        PHONEPE,
        UPI,
        CREDIT_CARD,
        DEBIT_CARD,
        NET_BANKING,
        COD,
        WALLET
    }

    public enum PaymentGateway {
        PHONEPE,
        RAZORPAY,
        STRIPE,
        PAYTM
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMerchantTransactionId() {
        return merchantTransactionId;
    }

    public void setMerchantTransactionId(String merchantTransactionId) {
        this.merchantTransactionId = merchantTransactionId;
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

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }

    public void setGateway(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public String getGatewayResponse() {
        return gatewayResponse;
    }

    public void setGatewayResponse(String gatewayResponse) {
        this.gatewayResponse = gatewayResponse;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
