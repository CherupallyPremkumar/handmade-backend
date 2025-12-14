package com.handmade.ecommerce.command;

import java.io.Serializable;

public class CheckoutSessionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sessionId;
    private String paymentOrderId;
    private String sellerId;
    private String checkoutUrl;
    private String pspType; // PSP type (RAZORPAY, STRIPE, etc.)

    public CheckoutSessionResponse() {
    }

    public CheckoutSessionResponse(String paymentOrderId, String sellerId, String checkoutUrl) {
        this.paymentOrderId = paymentOrderId;
        this.sellerId = sellerId;
        this.checkoutUrl = checkoutUrl;
    }

    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(String paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPspType() {
        return pspType;
    }

    public void setPspType(String pspType) {
        this.pspType = pspType;
    }
}
