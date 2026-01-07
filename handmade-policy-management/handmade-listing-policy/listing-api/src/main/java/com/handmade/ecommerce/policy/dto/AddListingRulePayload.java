package com.handmade.ecommerce.policy.dto;

import com.handmade.ecommerce.policy.domain.valueobject.ListingViolationType;
import java.io.Serializable;

public class AddListingRulePayload implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String ruleName;
    private ListingViolationType violationType;
    private String actionRequired;
    private Boolean required;
    private Double thresholdValue;

    // Getters and Setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String n) { this.ruleName = n; }
    public ListingViolationType getViolationType() { return violationType; }
    public void setViolationType(ListingViolationType t) { this.violationType = t; }
    public String getActionRequired() { return actionRequired; }
    public void setActionRequired(String r) { this.actionRequired = r; }
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean r) { this.required = r; }
    public Double getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(Double v) { this.thresholdValue = v; }
}
