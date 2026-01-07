package com.handmade.ecommerce.policy.dto;

import java.io.Serializable;

public class RemovePricingRulePayload implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String ruleName;

    // Getters and Setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
}
