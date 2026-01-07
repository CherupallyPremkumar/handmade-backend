package com.handmade.ecommerce.policy.dto;

import com.handmade.ecommerce.policy.domain.valueobject.PricingDecision;
import com.handmade.ecommerce.policy.domain.valueobject.PricingViolationType;

import java.util.ArrayList;
import java.util.List;

public class PriceValidationResult {
    private PricingDecision decision;
    private List<PricingViolationType> violations = new ArrayList<>();
    private String message;
    private String policyVersion;
    
    public PricingDecision getDecision() { return decision; }
    public void setDecision(PricingDecision decision) { this.decision = decision; }
    
    public List<PricingViolationType> getViolations() { return violations; }
    public void setViolations(List<PricingViolationType> violations) { this.violations = violations; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getPolicyVersion() { return policyVersion; }
    public void setPolicyVersion(String policyVersion) { this.policyVersion = policyVersion; }
}
