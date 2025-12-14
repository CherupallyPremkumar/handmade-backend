package com.handmade.ecommerce.rules.dto;

import com.handmade.ecommerce.core.model.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule Result
 * Contains the result of rule evaluation
 * This is engine-agnostic - works with Easy Rules and Drools
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleResult {

    /**
     * Final price after applying rules
     */
    private Money finalPrice;

    /**
     * Total discount amount
     */
    private Money totalDiscount;

    /**
     * Tax amount (if tax rules applied)
     */
    private Money taxAmount;

    /**
     * Tax description for display (e.g., "GST 18%")
     */
    private String taxDescription;

    /**
     * Tax rate that was applied
     */
    private BigDecimal taxRate;

    /**
     * List of rules that were applied
     */
    @Builder.Default
    private List<AppliedRuleInfo> appliedRules = new ArrayList<>();

    /**
     * Add an applied rule to the list
     */
    public void addAppliedRule(AppliedRuleInfo rule) {
        if (appliedRules == null) {
            appliedRules = new ArrayList<>();
        }
        appliedRules.add(rule);
    }
}
