package com.handmade.ecommerce.policy.domain.entity;

import com.handmade.ecommerce.policy.domain.aggregate.ReturnPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.ReturnViolationType;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "return_policy_rules")
public class ReturnPolicyRule extends BaseJpaEntity {

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private ReturnPolicy policy;

    @Column(name = "rule_name")
    private String ruleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "violation_type")
    private ReturnViolationType violationType;

    @Column(name = "required", nullable = false)
    private Boolean required = true;

    @Column(name = "threshold_value")
    private Integer thresholdValue;

    // Getters/Setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String n) { this.ruleName = n; }
    public void setPolicy(ReturnPolicy policy) { this.policy = policy; }
    public ReturnPolicy getPolicy() { return policy; }
    public ReturnViolationType getViolationType() { return violationType; }
    public void setViolationType(ReturnViolationType violationType) { this.violationType = violationType; }
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean required) { this.required = required; }
    public Integer getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(Integer thresholdValue) { this.thresholdValue = thresholdValue; }

    @Override
    protected String getPrefix() {
        return "RETURN_RULE";
    }
}
