package com.handmade.ecommerce.orchestration.seller.dto;

/**
 * Response DTO for verification steps
 * Returned by Identity, Tax, and Bank verification commands
 */
public class VerificationResponse {
    
    private String sessionUrl;
    private String verificationId;
    private String status; // PENDING, IN_PROGRESS, COMPLETED, FAILED
    private String provider;
    
    public VerificationResponse() {
    }
    
    public VerificationResponse(String sessionUrl, String verificationId) {
        this.sessionUrl = sessionUrl;
        this.verificationId = verificationId;
    }
    
    public String getSessionUrl() {
        return sessionUrl;
    }
    
    public void setSessionUrl(String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }
    
    public String getVerificationId() {
        return verificationId;
    }
    
    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public void setProvider(String provider) {
        this.provider = provider;
    }
}
