package com.handmade.ecommerce.risk.client.model;

/**
 * Request model for Sift API.
 * 
 * See: https://sift.com/developers/docs/curl/events-api/reserved-events/transaction
 */
public class SiftRequest {
    
    private String userId;
    private Long amount; // Amount in micros (e.g., $10.50 = 10500000)
    private String currencyCode;
    private String ip;
    private String sessionId;
    private String transactionType = "$transaction";
    
    // Getters and setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Long getAmount() {
        return amount;
    }
    
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    
    public String getCurrencyCode() {
        return currencyCode;
    }
    
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    
    public String getIp() {
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
