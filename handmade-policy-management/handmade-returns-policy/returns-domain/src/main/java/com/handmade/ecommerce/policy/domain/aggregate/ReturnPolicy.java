package com.handmade.ecommerce.policy.domain.aggregate;

import com.handmade.ecommerce.policy.domain.entity.ReturnPolicyRule;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "return_policies")
public class ReturnPolicy extends AbstractJpaStateEntity {

    @Column(name = "policy_version", length = 20, nullable = false)
    private String policyVersion;

    @Column(name = "country_code", length = 3)
    private String countryCode;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "deprecated_date")
    private LocalDate deprecatedDate;
    
    @Column(name = "return_window_days")
    private Integer returnWindowDays;
    
    @Column(name = "restocking_fee_percent")
    private Integer restockingFeePercent;
    
    @Column(name = "is_free_return")
    private Boolean isFreeReturn;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ReturnPolicyRule> rules = new ArrayList<>();

    public void approve(String approvedBy) {
        this.effectiveDate = LocalDate.now();
    }

    public void deprecate() {
        this.deprecatedDate = LocalDate.now();
    }

    // Getters/Setters
    public String getPolicyVersion() { return policyVersion; }
    public void setPolicyVersion(String v) { this.policyVersion = v; }
    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String c) { this.countryCode = c; }
    public String getCategory() { return category; }
    public void setCategory(String c) { this.category = c; }
    public List<ReturnPolicyRule> getRules() { return rules; }

    public void addRule(ReturnPolicyRule rule) {
        rule.setPolicy(this);
        this.rules.add(rule);
    }

    public void updateRule(ReturnPolicyRule rule) {
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
        this.rules.removeIf(r -> r.getRuleName().equals(ruleName));
    }
}
