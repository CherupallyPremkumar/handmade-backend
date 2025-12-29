package com.handmade.ecommerce.orchestration.seller.dto;

/**
 * Response DTO for compliance check
 */
public class ComplianceCheckResponse {
    
    private String sellerId;
    private int complianceScore;
    private boolean compliancePassed;
    private String[] violations;
    private String riskLevel; // LOW, MEDIUM, HIGH
    
    public ComplianceCheckResponse() {
    }
    
    public String getSellerId() {
        return sellerId;
    }
    
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    
    public int getComplianceScore() {
        return complianceScore;
    }
    
    public void setComplianceScore(int complianceScore) {
        this.complianceScore = complianceScore;
    }
    
    public boolean isCompliancePassed() {
        return compliancePassed;
    }
    
    public void setCompliancePassed(boolean compliancePassed) {
        this.compliancePassed = compliancePassed;
    }
    
    public String[] getViolations() {
        return violations;
    }
    
    public void setViolations(String[] violations) {
        this.violations = violations;
    }
    
    public String getRiskLevel() {
        return riskLevel;
    }
    
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
