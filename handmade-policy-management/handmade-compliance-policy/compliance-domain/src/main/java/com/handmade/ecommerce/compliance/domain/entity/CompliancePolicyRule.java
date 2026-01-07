package com.handmade.ecommerce.compliance.domain.entity;

import com.handmade.ecommerce.compliance.domain.valueobject.ComplianceSeverity;
import org.chenile.jpautils.entity.BaseJpaEntity;
import jakarta.persistence.*;
import com.handmade.ecommerce.compliance.domain.persistence.JsonAttributeConverter;
import java.util.Map;

/**
 * Compliance policy rule entity
 * Defines specific compliance requirements
 */
@Entity
@Table(name = "compliance_policy_rules", indexes = {
        @Index(name = "idx_compliance_rules", columnList = "policy_id, rule_order")
})
public class CompliancePolicyRule extends BaseJpaEntity {

    @Column(name = "policy_id", length = 255, nullable = false)
    private String policyId;

    /**
     * Rule name (e.g., "aml_screening", "tax_compliance")
     */
    @Column(name = "rule_name", length = 100, nullable = false)
    private String ruleName;

    /**
     * Rule execution order
     */
    @Column(name = "rule_order", nullable = false)
    private Integer ruleOrder;

    /**
     * Severity level
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "severity", length = 20, nullable = false)
    private ComplianceSeverity severity;

    /**
     * Is this rule required for activation
     */
    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

    /**
     * Compliance check provider (e.g., "dow_jones", "world_check")
     */
    @Column(name = "provider_name", length = 100)
    private String providerName;

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

    public ComplianceSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(ComplianceSeverity severity) {
        this.severity = severity;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Map<String, Object> getRuleConfig() {
        return ruleConfig;
    }

    public void setRuleConfig(Map<String, Object> ruleConfig) {
        this.ruleConfig = ruleConfig;
    }
}
