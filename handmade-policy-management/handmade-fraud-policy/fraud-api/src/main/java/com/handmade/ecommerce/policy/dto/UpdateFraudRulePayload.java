package com.handmade.ecommerce.policy.dto;

import com.handmade.ecommerce.policy.domain.valueobject.FraudViolationType;
import java.io.Serializable;

public class UpdateFraudRulePayload implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String ruleName;
    private FraudViolationType violationType;
    private Boolean required;
    private Integer thresholdValue;

    // Getters and Setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String n) { this.ruleName = n; }
    public FraudViolationType getViolationType() { return violationType; }
    public void setViolationType(FraudViolationType t) { this.violationType = t; }
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean r) { this.required = r; }
    public Integer getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(Integer v) { this.thresholdValue = v; }
}
