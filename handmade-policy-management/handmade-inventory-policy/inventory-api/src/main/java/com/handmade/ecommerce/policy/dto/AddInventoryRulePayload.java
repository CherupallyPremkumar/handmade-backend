package com.handmade.ecommerce.policy.dto;

import com.handmade.ecommerce.policy.domain.valueobject.InventoryViolationType;
import java.io.Serializable;

public class AddInventoryRulePayload implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String ruleName;
    private InventoryViolationType violationType;
    private Integer thresholdValue;
    private Boolean required;

    // Getters and Setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String n) { this.ruleName = n; }
    public InventoryViolationType getViolationType() { return violationType; }
    public void setViolationType(InventoryViolationType t) { this.violationType = t; }
    public Integer getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(Integer v) { this.thresholdValue = v; }
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean r) { this.required = r; }
}
