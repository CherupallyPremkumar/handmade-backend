package com.handmade.ecommerce.orchestrator.seller.model;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import jakarta.persistence.Entity;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.util.HashMap;
import java.util.Map;

@Entity
public class SellerOnboardingContext extends AbstractJpaStateEntity {
    
    private static final long serialVersionUID = 1L;
    
    // Data storage (like HashMap)
    private Map<String, Object> data = new HashMap<>();
    
    // ========== Helper Methods for Data Access ==========
    
    public void put(String key, Object value) {
        data.put(key, value);
    }
    
    public Object get(String key) {
        return data.get(key);
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    // ========== Input Fields (from Frontend) ==========
    
    public void setEmail(String email) {
        put("email", email);
    }
    
    public String getEmail() {
        return (String) get("email");
    }
    
    public void setBusinessName(String businessName) {
        put("businessName", businessName);
    }
    
    public String getBusinessName() {
        return (String) get("businessName");
    }
    
    public void setCountryCode(String countryCode) {
        put("countryCode", countryCode);
        put("country", countryCode); // For routing
    }
    
    public String getCountryCode() {
        return (String) get("countryCode");
    }
    
    public void setSellerType(String sellerType) {
        put("sellerType", sellerType);
    }
    
    public String getSellerType() {
        return (String) get("sellerType");
    }
    
    // ========== Workflow State Fields ==========
    
    public void setSellerId(String sellerId) {
        put("sellerId", sellerId);
    }
    
    public String getSellerId() {
        return (String) get("sellerId");
    }
    
    public void setOnboardingPolicy(OnboardingPolicy policy) {
        put("onboardingPolicy", policy);
    }
    
    public OnboardingPolicy getOnboardingPolicy() {
        return (OnboardingPolicy) get("onboardingPolicy");
    }
    
    // ========== Policy Evaluation Flags ==========
    
    public void setIdentityRequired(boolean required) {
        put("identityRequired", required);
    }
    
    public boolean isIdentityRequired() {
        return Boolean.TRUE.equals(get("identityRequired"));
    }
    
    public void setTaxRequired(boolean required) {
        put("taxRequired", required);
    }
    
    public boolean isTaxRequired() {
        return Boolean.TRUE.equals(get("taxRequired"));
    }
    
    public void setBankRequired(boolean required) {
        put("bankRequired", required);
    }
    
    public boolean isBankRequired() {
        return Boolean.TRUE.equals(get("bankRequired"));
    }
    
    // ========== Verification Session URLs ==========
    
    public void setIdentitySessionUrl(String url) {
        put("identitySessionUrl", url);
    }
    
    public String getIdentitySessionUrl() {
        return (String) get("identitySessionUrl");
    }
    
    public void setTaxSessionUrl(String url) {
        put("taxSessionUrl", url);
    }
    
    public String getTaxSessionUrl() {
        return (String) get("taxSessionUrl");
    }
    
    public void setBankSessionUrl(String url) {
        put("bankSessionUrl", url);
    }
    
    public String getBankSessionUrl() {
        return (String) get("bankSessionUrl");
    }
    
    // ========== Compliance Results ==========
    
    public void setComplianceScore(int score) {
        put("complianceScore", score);
    }
    
    public int getComplianceScore() {
        Integer score = (Integer) get("complianceScore");
        return score != null ? score : 0;
    }
    
    public void setCompliancePassed(boolean passed) {
        put("compliancePassed", passed);
    }
    
    public boolean isCompliancePassed() {
        return Boolean.TRUE.equals(get("compliancePassed"));
    }
    
    // ========== Approval Flags ==========
    
    public void setRequiresManualApproval(boolean required) {
        put("requiresManualApproval", required);
    }
    
    public boolean requiresManualApproval() {
        return Boolean.TRUE.equals(get("requiresManualApproval"));
    }
    
    // ========== Helper Methods ==========
    
    /**
     * Get step configuration from policy
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getStepConfig(String stepName) {
        OnboardingPolicy policy = getOnboardingPolicy();
        if (policy == null) {
            return new HashMap<>();
        }
        // Extract config from policy rules
        return (Map<String, Object>) get(stepName + "Config");
    }
    
    /**
     * Check if workflow is complete
     */
    public boolean isWorkflowComplete() {
        return getSellerId() != null && 
               (isCompliancePassed() || get("rejected") != null);
    }
    
    /**
     * Get current workflow stage
     */
    public String getCurrentStage() {
        if (getSellerId() == null) return "POLICY_RESOLUTION";
        if (getIdentitySessionUrl() != null) return "IDENTITY_VERIFICATION";
        if (getTaxSessionUrl() != null) return "TAX_VERIFICATION";
        if (getBankSessionUrl() != null) return "BANK_VERIFICATION";
        if (get("complianceScore") != null) return "COMPLIANCE_CHECK";
        return "APPROVAL";
    }
}
