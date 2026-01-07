package com.handmade.ecommerce.policy.domain.entity;

import com.handmade.ecommerce.policy.domain.aggregate.InventoryPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.InventoryViolationType;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "inventory_policy_rules")
public class InventoryPolicyRule extends BaseJpaEntity {

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private InventoryPolicy policy;

    @Column(name = "rule_name")
    private String ruleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "violation_type")
    private InventoryViolationType violationType;

    @Column(name = "threshold_value")
    private Integer thresholdValue;

    @Column(name = "required", nullable = false)
    private Boolean required = true;

    // Getters/Setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String n) { this.ruleName = n; }
    public void setPolicy(InventoryPolicy policy) { this.policy = policy; }
    public InventoryPolicy getPolicy() { return policy; }
    public InventoryViolationType getViolationType() { return violationType; }
    public void setViolationType(InventoryViolationType t) { this.violationType = t; }
    public Integer getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(Integer v) { this.thresholdValue = v; }
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean r) { this.required = r; }

    @Override
    protected String getPrefix() {
        return "INVENTORY_RULE";
    }
}
