package com.handmade.ecommerce.policy.domain.entity;

import com.handmade.ecommerce.policy.domain.aggregate.ListingPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.ListingViolationType;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "listing_policy_rules")
public class ListingPolicyRule extends BaseJpaEntity {

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private ListingPolicy policy;

    @Column(name = "rule_name", length = 100, nullable = false)
    private String ruleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "violation_type")
    private ListingViolationType violationType;

    @Column(name = "action_required")
    private String actionRequired; // BLOCK, WARN, REVIEW

    @Column(name = "required")
    private Boolean required;

    @Column(name = "threshold_value")
    private Double thresholdValue;

    // Getters and Setters
    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String n) {
        this.ruleName = n;
    }

    public ListingViolationType getViolationType() {
        return violationType;
    }

    public void setViolationType(ListingViolationType t) {
        this.violationType = t;
    }

    public String getActionRequired() {
        return actionRequired;
    }

    public void setActionRequired(String r) {
        this.actionRequired = r;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean r) {
        this.required = r;
    }

    public Double getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(Double v) {
        this.thresholdValue = v;
    }

    public void setPolicy(ListingPolicy policy) {
        this.policy = policy;
    }

    public ListingPolicy getPolicy() {
        return policy;
    }
}
