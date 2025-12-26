package com.handmade.ecommerce.command;

import com.handmade.ecommerce.pricing.model.AdjustmentType;
import com.handmade.ecommerce.pricing.model.PriceRuleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CreatePriceRuleCommand
 * DTO for creating dynamic pricing rules
 * 
 * Scalability: JSON-based conditions allow flexible rule definitions
 * Examples:
 * - Bulk discount: {"minQuantity": 10}
 * - Customer segment: {"segment": "VIP"}
 * - Time-based: {"dayOfWeek": "FRIDAY", "hour": 18}
 */
public class CreatePriceRuleCommand {

    private PriceRuleType ruleType; // BULK_DISCOUNT, PROMOTIONAL, CUSTOMER_SEGMENT, etc.
    private String ruleCondition; // JSON string with rule conditions
    private AdjustmentType adjustmentType; // PERCENTAGE_OFF, FIXED_AMOUNT_OFF, etc.
    private BigDecimal adjustmentValue; // 15 (for 15% or $15 depending on adjustmentType)
    private Integer priority; // 1 = highest priority, rules applied in order
    private LocalDateTime validFrom; // When rule becomes active
    private LocalDateTime validUntil; // When rule expires

    // Getters and Setters
    public PriceRuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(PriceRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleCondition() {
        return ruleCondition;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    public AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(AdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public BigDecimal getAdjustmentValue() {
        return adjustmentValue;
    }

    public void setAdjustmentValue(BigDecimal adjustmentValue) {
        this.adjustmentValue = adjustmentValue;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }
}
