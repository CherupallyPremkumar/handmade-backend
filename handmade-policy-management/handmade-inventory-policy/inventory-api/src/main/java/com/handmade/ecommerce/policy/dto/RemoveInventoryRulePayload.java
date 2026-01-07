package com.handmade.ecommerce.policy.dto;

import java.io.Serializable;

public class RemoveInventoryRulePayload implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String ruleName;

    // Getters and Setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String n) { this.ruleName = n; }
}
