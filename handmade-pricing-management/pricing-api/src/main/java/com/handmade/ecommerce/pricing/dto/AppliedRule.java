package com.handmade.ecommerce.pricing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Applied price rule details
 * Shows which rules were applied and their effect
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppliedRule {

    /**
     * Rule ID
     */
    private String ruleId;

    /**
     * Rule name for display
     */
    private String ruleName;

    /**
     * Rule type (BULK_DISCOUNT, SEASONAL, etc.)
     */
    private String ruleType;

    /**
     * Adjustment amount (discount/markup)
     */
    private BigDecimal adjustment;

    /**
     * Adjustment percentage (if percentage-based)
     */
    private BigDecimal percentage;

    /**
     * Human-readable description
     */
    private String description;
}
