package com.handmade.ecommerce.paymentexecutor.model;

import java.io.Serializable;

/**
 * CheckoutSession
 * 
 * Represents a checkout session created with a PSP for hosted payment page flow.
 * Contains the session ID and checkout URL where the user will complete payment.
 */
public class CheckoutSession implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * PSP's session identifier
     */
    private String sessionId;
    
    /**
     * Hosted payment page URL where user completes payment
     */
    private String checkoutUrl;
    
    /**
     * PSP's payment order identifier
     */
    private String pspOrderId;
    
    /**
     * Session expiration time in seconds
     */
    private Long expiresIn;
    
    /**
     * PSP type (RAZORPAY, STRIPE, etc.)
     */
    private String pspType;
    
    // Constructors
    
    public CheckoutSession() {
    }
    
    public CheckoutSession(String sessionId, String checkoutUrl) {
        this.sessionId = sessionId;
        this.checkoutUrl = checkoutUrl;
    }
    
    public CheckoutSession(String sessionId, String checkoutUrl, String pspOrderId, String pspType) {
        this.sessionId = sessionId;
        this.checkoutUrl = checkoutUrl;
        this.pspOrderId = pspOrderId;
        this.pspType = pspType;
    }
    
    // Getters and Setters
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getCheckoutUrl() {
        return checkoutUrl;
    }
    
    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }
    
    public String getPspOrderId() {
        return pspOrderId;
    }
    
    public void setPspOrderId(String pspOrderId) {
        this.pspOrderId = pspOrderId;
    }
    
    public Long getExpiresIn() {
        return expiresIn;
    }
    
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
    
    public String getPspType() {
        return pspType;
    }
    
    public void setPspType(String pspType) {
        this.pspType = pspType;
    }
    
    @Override
    public String toString() {
        return "CheckoutSession{" +
                "sessionId='" + sessionId + '\'' +
                ", checkoutUrl='" + checkoutUrl + '\'' +
                ", pspOrderId='" + pspOrderId + '\'' +
                ", expiresIn=" + expiresIn +
                ", pspType='" + pspType + '\'' +
                '}';
    }
}
