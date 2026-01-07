package com.handmade.ecommerce.policy.domain.aggregate;

import com.handmade.ecommerce.policy.domain.entity.PerformanceThreshold;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "performance_policies")
public class PerformancePolicy extends AbstractJpaStateEntity {

    @Column(name = "policy_version", length = 20, nullable = false)
    private String policyVersion;

    @Column(name = "country_code", length = 3)
    private String countryCode;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PerformanceThreshold> thresholds = new ArrayList<>();

    public void approve(String approvedBy) {
        this.effectiveDate = LocalDate.now();
    }

    public void deprecate() {
    }

    public List<PerformanceThreshold> getThresholds() { return thresholds; }

    public void addThreshold(PerformanceThreshold threshold) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Thresholds can only be added to DRAFT policies");
        }
        threshold.setPolicy(this);
        this.thresholds.add(threshold);
    }

    public void updateThreshold(PerformanceThreshold threshold) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Thresholds can only be updated in DRAFT policies");
        }
        this.thresholds.stream()
            .filter(t -> t.getViolationType().name().equals(threshold.getViolationType().name()))
            .findFirst()
            .ifPresent(t -> {
                t.setWarningThreshold(threshold.getWarningThreshold());
                t.setCriticalThreshold(threshold.getCriticalThreshold());
                t.setRequired(threshold.getRequired());
            });
    }

    public void removeThreshold(String violationType) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Thresholds can only be removed from DRAFT policies");
        }
        this.thresholds.removeIf(t -> t.getViolationType().name().equals(violationType));
    }

    // Getters/Setters
    public String getPolicyVersion() { return policyVersion; }
    public void setPolicyVersion(String v) { this.policyVersion = v; }
}
