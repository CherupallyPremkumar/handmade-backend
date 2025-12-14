package com.handmade.ecommerce.rules.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Applied Rule Info
 * Information about a rule that was applied
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppliedRuleInfo {

    /**
     * Rule ID from database
     */
    private String ruleId;

    /**
     * Rule name for display
     */
    private String ruleName;

    /**
     * Rule type (BULK_DISCOUNT, VIP_DISCOUNT, etc.)
     */
    private String ruleType;

    /**
     * Discount percentage that was applied
     */
    private BigDecimal percentage;

    /**
     * Adjustment amount
     */
    private BigDecimal adjustment;

    /**
     * Human-readable description
     */
    private String description;
}
