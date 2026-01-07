package com.handmade.ecommerce.policy.dto;

import com.handmade.ecommerce.policy.domain.valueobject.PricingViolationType;

public class AddPricingPolicyRulePayload {
    private String ruleName;
    private Integer ruleOrder;
    private Boolean required;
    private PricingViolationType violationType;
    private Integer thresholdValue;
    private String action;
    
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    
    public Integer getRuleOrder() { return ruleOrder; }
    public void setRuleOrder(Integer ruleOrder) { this.ruleOrder = ruleOrder; }
    
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean required) { this.required = required; }
    
    public PricingViolationType getViolationType() { return violationType; }
    public void setViolationType(PricingViolationType violationType) { this.violationType = violationType; }
    
    public Integer getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(Integer thresholdValue) { this.thresholdValue = thresholdValue; }
    
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}
