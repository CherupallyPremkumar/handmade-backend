package com.handmade.ecommerce.commission.dto;

import com.handmade.ecommerce.commission.domain.valueobject.CommissionFeeType;
import java.io.Serializable;
import java.math.BigDecimal;

public class UpdateCommissionRulePayload implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String ruleName;
    private CommissionFeeType feeType;
    private BigDecimal thresholdValue;
    private Boolean required;

    // Getters and Setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public CommissionFeeType getFeeType() { return feeType; }
    public void setFeeType(CommissionFeeType feeType) { this.feeType = feeType; }

    public BigDecimal getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(BigDecimal thresholdValue) { this.thresholdValue = thresholdValue; }

    public Boolean getRequired() { return required; }
    public void setRequired(Boolean required) { this.required = required; }
}
