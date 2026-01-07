package com.handmade.ecommerce.policy.dto;

import com.handmade.ecommerce.policy.domain.valueobject.PerformanceViolationType;
import java.io.Serializable;

public class UpdatePerformanceThresholdPayload implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String ruleName;
    private PerformanceViolationType violationType;
    private Double warningThreshold;
    private Double criticalThreshold;
    private Boolean required;

    // Getters and Setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String n) { this.ruleName = n; }
    public PerformanceViolationType getViolationType() { return violationType; }
    public void setViolationType(PerformanceViolationType t) { this.violationType = t; }
    public Double getWarningThreshold() { return warningThreshold; }
    public void setWarningThreshold(Double v) { this.warningThreshold = v; }
    public Double getCriticalThreshold() { return criticalThreshold; }
    public void setCriticalThreshold(Double v) { this.criticalThreshold = v; }
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean r) { this.required = r; }
}
