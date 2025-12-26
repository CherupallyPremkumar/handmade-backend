package com.handmade.ecommerce.orchestrator.seller.model;

/**
 * Response DTO for seller onboarding workflow
 */
public class OnboardingResponse {
    
    private String sellerId;
    private String workflowId;
    private String currentStage;
    private String policyVersion;
    
    // Verification URLs (if applicable)
    private String identitySessionUrl;
    private String taxSessionUrl;
    private String bankSessionUrl;
    
    // Status flags
    private boolean identityRequired;
    private boolean taxRequired;
    private boolean bankRequired;
    private boolean requiresManualApproval;
    
    // Compliance
    private Integer complianceScore;
    private Boolean compliancePassed;
    
    public OnboardingResponse() {
    }
    
    public OnboardingResponse(String sellerId, String workflowId) {
        this.sellerId = sellerId;
        this.workflowId = workflowId;
    }
    
    // Getters and Setters
    
    public String getSellerId() {
        return sellerId;
    }
    
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    
    public String getWorkflowId() {
        return workflowId;
    }
    
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
    
    public String getCurrentStage() {
        return currentStage;
    }
    
    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }
    
    public String getPolicyVersion() {
        return policyVersion;
    }
    
    public void setPolicyVersion(String policyVersion) {
        this.policyVersion = policyVersion;
    }
    
    public String getIdentitySessionUrl() {
        return identitySessionUrl;
    }
    
    public void setIdentitySessionUrl(String identitySessionUrl) {
        this.identitySessionUrl = identitySessionUrl;
    }
    
    public String getTaxSessionUrl() {
        return taxSessionUrl;
    }
    
    public void setTaxSessionUrl(String taxSessionUrl) {
        this.taxSessionUrl = taxSessionUrl;
    }
    
    public String getBankSessionUrl() {
        return bankSessionUrl;
    }
    
    public void setBankSessionUrl(String bankSessionUrl) {
        this.bankSessionUrl = bankSessionUrl;
    }
    
    public boolean isIdentityRequired() {
        return identityRequired;
    }
    
    public void setIdentityRequired(boolean identityRequired) {
        this.identityRequired = identityRequired;
    }
    
    public boolean isTaxRequired() {
        return taxRequired;
    }
    
    public void setTaxRequired(boolean taxRequired) {
        this.taxRequired = taxRequired;
    }
    
    public boolean isBankRequired() {
        return bankRequired;
    }
    
    public void setBankRequired(boolean bankRequired) {
        this.bankRequired = bankRequired;
    }
    
    public boolean isRequiresManualApproval() {
        return requiresManualApproval;
    }
    
    public void setRequiresManualApproval(boolean requiresManualApproval) {
        this.requiresManualApproval = requiresManualApproval;
    }
    
    public Integer getComplianceScore() {
        return complianceScore;
    }
    
    public void setComplianceScore(Integer complianceScore) {
        this.complianceScore = complianceScore;
    }
    
    public Boolean getCompliancePassed() {
        return compliancePassed;
    }
    
    public void setCompliancePassed(Boolean compliancePassed) {
        this.compliancePassed = compliancePassed;
    }
}
