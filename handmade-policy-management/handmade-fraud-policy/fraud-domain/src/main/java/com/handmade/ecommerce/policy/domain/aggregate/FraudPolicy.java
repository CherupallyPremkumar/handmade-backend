package com.handmade.ecommerce.policy.domain.aggregate;

import com.handmade.ecommerce.policy.domain.entity.FraudPolicyRule;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fraud_policies")
public class FraudPolicy extends AbstractJpaStateEntity {

    @Column(name = "policy_version", length = 20, nullable = false)
    private String policyVersion;

    @Column(name = "country_code", length = 3)
    private String countryCode;

    @Column(name = "risk_threshold")
    private Integer riskThreshold;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FraudPolicyRule> rules = new ArrayList<>();

    public void approve(String approvedBy) {
        this.effectiveDate = LocalDate.now();
    }

    public void deprecate() {
    }

    // Getters/Setters
    public String getPolicyVersion() { return policyVersion; }
    public void setPolicyVersion(String v) { this.policyVersion = v; }
    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String c) { this.countryCode = c; }
    public List<FraudPolicyRule> getRules() { return rules; }

    public void addRule(FraudPolicyRule rule) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Rules can only be added to DRAFT policies");
        }
        rule.setPolicy(this);
        this.rules.add(rule);
    }

    public void updateRule(FraudPolicyRule rule) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Rules can only be updated in DRAFT policies");
        }
        this.rules.stream()
            .filter(r -> r.getRuleName().equals(rule.getRuleName()))
            .findFirst()
            .ifPresent(r -> {
                r.setViolationType(rule.getViolationType());
                r.setRequired(rule.getRequired());
                r.setThresholdValue(rule.getThresholdValue());
            });
    }

    public void removeRule(String ruleName) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Rules can only be removed from DRAFT policies");
        }
        this.rules.removeIf(r -> r.getRuleName().equals(ruleName));
    }
}
