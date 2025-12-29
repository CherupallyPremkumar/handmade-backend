package com.handmade.ecommerce.payout.domain.entity;

import com.handmade.ecommerce.payout.domain.valueobject.PayoutFrequency;
import org.chenile.jpautils.entity.BaseJpaEntity;

import jakarta.persistence.*;
import com.handmade.ecommerce.payout.domain.persistence.JsonAttributeConverter;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Payout policy rule entity
 * Defines specific payout constraints and configurations
 * 
 * Rules are ordered and evaluated in sequence
 * First matching rule is applied to seller payout
 */
@Entity
@Table(name = "payout_policy_rules", indexes = {
        @Index(name = "idx_policy_rules", columnList = "policy_id, rule_order")
})
public class PayoutPolicyRule extends BaseJpaEntity {

    @Column(name = "policy_id", length = 255, nullable = false)
    private String policyId;

    /**
     * Rule name (e.g., "standard_payout", "accelerated_payout")
     */
    @Column(name = "rule_name", length = 100, nullable = false)
    private String ruleName;

    /**
     * Rule execution order (lower = higher priority)
     */
    @Column(name = "rule_order", nullable = false)
    private Integer ruleOrder;

    /**
     * Payout frequency for this rule
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", length = 20, nullable = false)
    private PayoutFrequency frequency;

    /**
     * Minimum threshold for this rule
     */
    @Column(name = "threshold", precision = 19, scale = 4)
    private BigDecimal threshold;

    /**
     * Hold period override (days)
     */
    @Column(name = "hold_period_days")
    private Integer holdPeriodDays;

    /**
     * Eligibility criteria
     */
    @Convert(converter = JsonAttributeConverter.class)
    @Column(name = "eligibility_criteria")
    private Map<String, Object> eligibilityCriteria;

    /**
     * Rule-specific configuration
     */
    @Convert(converter = JsonAttributeConverter.class)
    @Column(name = "rule_config")
    private Map<String, Object> ruleConfig;

    // ========== GETTERS AND SETTERS ==========

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getRuleOrder() {
        return ruleOrder;
    }

    public void setRuleOrder(Integer ruleOrder) {
        this.ruleOrder = ruleOrder;
    }

    public PayoutFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(PayoutFrequency frequency) {
        this.frequency = frequency;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }

    public Integer getHoldPeriodDays() {
        return holdPeriodDays;
    }

    public void setHoldPeriodDays(Integer holdPeriodDays) {
        this.holdPeriodDays = holdPeriodDays;
    }

    public Map<String, Object> getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public void setEligibilityCriteria(Map<String, Object> eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public Map<String, Object> getRuleConfig() {
        return ruleConfig;
    }

    public void setRuleConfig(Map<String, Object> ruleConfig) {
        this.ruleConfig = ruleConfig;
    }
}
