package com.handmade.ecommerce.command;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Normalized webhook payload extracted from PSP-specific format.
 */
public class WebhookPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    private String event;
    private String paymentId; // Our internal payment ID (order_id in Razorpay)
    private String pspPaymentId; // PSP's payment ID (pay_id in Razorpay)
    private BigDecimal amount;
    private String currency;
    private String status;
    private Instant timestamp;
    private String rawPayload;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPspPaymentId() {
        return pspPaymentId;
    }

    public void setPspPaymentId(String pspPaymentId) {
        this.pspPaymentId = pspPaymentId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getRawPayload() {
        return rawPayload;
    }

    public void setRawPayload(String rawPayload) {
        this.rawPayload = rawPayload;
    }
}
