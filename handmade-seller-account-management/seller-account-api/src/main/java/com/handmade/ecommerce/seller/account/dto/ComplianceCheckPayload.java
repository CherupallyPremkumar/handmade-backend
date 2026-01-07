package com.handmade.ecommerce.seller.account.dto;

/**
 * Step 7: Compliance Check Payload
 * Input for running compliance check
 */
public class ComplianceCheckPayload {
    
    private String sellerId;
    private String policyVersion;
    
    public ComplianceCheckPayload() {
    }
    
    public ComplianceCheckPayload(String sellerId, String policyVersion) {
        this.sellerId = sellerId;
        this.policyVersion = policyVersion;
    }
    
    public String getSellerId() {
        return sellerId;
    }
    
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    
    public String getPolicyVersion() {
        return policyVersion;
    }
    
    public void setPolicyVersion(String policyVersion) {
        this.policyVersion = policyVersion;
    }
}
