package com.handmade.ecommerce.policy.domain.entity;

import com.handmade.ecommerce.policy.domain.aggregate.FraudPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.FraudViolationType;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "fraud_policy_rules")
public class FraudPolicyRule extends BaseJpaEntity {

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private FraudPolicy policy;

    @Column(name = "rule_name", length = 100, nullable = false)
    private String ruleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "violation_type")
    private FraudViolationType violationType;

    @Column(name = "required", nullable = false)
    private Boolean required = true;

    @Column(name = "threshold_value")
    private Integer thresholdValue;

    // Getters/Setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String n) { this.ruleName = n; }
    public void setPolicy(FraudPolicy policy) { this.policy = policy; }
    public FraudPolicy getPolicy() { return policy; }
    public FraudViolationType getViolationType() { return violationType; }
    public void setViolationType(FraudViolationType t) { this.violationType = t; }
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean r) { this.required = r; }
    public Integer getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(Integer v) { this.thresholdValue = v; }

    @Override
    protected String getPrefix() {
        return "FRAUD_RULE";
    }
}
