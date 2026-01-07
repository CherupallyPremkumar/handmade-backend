package com.handmade.ecommerce.policy.domain.entity;

import org.chenile.jpautils.entity.BaseJpaEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import java.util.Map;
import com.handmade.ecommerce.policy.domain.aggregate.PricingPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.PricingViolationType;

/**
 * Individual pricing rule within a pricing policy
 * 
 * Defines:
 * - What type of violation to detect
 * - Threshold values
 * - Action to take (BLOCK, WARN, REVIEW)
 */
@Entity
@Table(name = "pricing_policy_rules",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"policy_id", "rule_name"})
       },
       indexes = {
           @Index(name = "idx_policy_rules", columnList = "policy_id, rule_order")
       })
public class PricingPolicyRule extends BaseJpaEntity {


    public PricingPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(PricingPolicy policy) {
        this.policy = policy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", insertable = false, updatable = false)
    private PricingPolicy policy;

    @Column(name = "policy_id", length = 255, nullable = false)
    private String policyId;
    
    /**
     * Rule name
     * Examples: "price_gouging_check", "markup_limit_check", "frequent_change_check"
     */
    @Column(name = "rule_name", length = 100, nullable = false)
    private String ruleName;
    
    /**
     * Execution order (1, 2, 3...)
     */
    @Column(name = "rule_order", nullable = false)
    private Integer ruleOrder;
    
    /**
     * Is this rule required?
     */
    @Column(name = "required", nullable = false)
    private Boolean required = true;
    
    /**
     * Type of violation this rule detects
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "violation_type", length = 50)
    private PricingViolationType violationType;
    
    /**
     * Threshold value (interpretation depends on violation type)
     * Examples:
     * - PRICE_GOUGING: max price increase percentage (e.g., 200 = 200%)
     * - EXCESSIVE_MARKUP: max markup percentage (e.g., 300 = 300%)
     * - FREQUENT_CHANGES: max changes per day (e.g., 3)
     */
    @Column(name = "threshold_value")
    private Integer thresholdValue;
    
    /**
     * Rule-specific configuration (JSON)
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rule_config", columnDefinition = "jsonb")
    private Map<String, Object> ruleConfig;
    
    /**
     * Action to take when rule is violated
     * BLOCK, WARN, REVIEW
     */
    @Column(name = "action", length = 20)
    private String action = "WARN";
    
    // ========== GETTERS AND SETTERS ==========
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
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
    
    public Boolean getRequired() {
        return required;
    }
    
    public void setRequired(Boolean required) {
        this.required = required;
    }
    
    public Boolean isRequired() {
        return required != null && required;
    }
    
    public PricingViolationType getViolationType() {
        return violationType;
    }
    
    public void setViolationType(PricingViolationType violationType) {
        this.violationType = violationType;
    }
    
    public Integer getThresholdValue() {
        return thresholdValue;
    }
    
    public void setThresholdValue(Integer thresholdValue) {
        this.thresholdValue = thresholdValue;
    }
    
    public Map<String, Object> getRuleConfig() {
        return ruleConfig;
    }
    
    public void setRuleConfig(Map<String, Object> ruleConfig) {
        this.ruleConfig = ruleConfig;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    protected String getPrefix() {
        return "PRICING_RULE";
    }
}
