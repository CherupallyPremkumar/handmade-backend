package com.handmade.ecommerce.policy.dto;

import com.handmade.ecommerce.policy.domain.valueobject.ReturnViolationType;
import java.io.Serializable;

public class UpdateReturnRulePayload implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String ruleName;
    private ReturnViolationType violationType;
    private Boolean required;
    private Integer thresholdValue;

    // Getters and Setters
    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public ReturnViolationType getViolationType() {
        return violationType;
    }

    public void setViolationType(ReturnViolationType violationType) {
        this.violationType = violationType;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(Integer thresholdValue) {
        this.thresholdValue = thresholdValue;
    }
}
