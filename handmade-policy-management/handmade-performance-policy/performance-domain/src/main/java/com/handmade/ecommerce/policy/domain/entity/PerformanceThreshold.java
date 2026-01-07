package com.handmade.ecommerce.policy.domain.entity;

import com.handmade.ecommerce.policy.domain.aggregate.PerformancePolicy;
import com.handmade.ecommerce.policy.domain.valueobject.PerformanceViolationType;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "performance_thresholds")
public class PerformanceThreshold extends BaseJpaEntity {

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private PerformancePolicy policy;

    @Enumerated(EnumType.STRING)
    @Column(name = "violation_type")
    private PerformanceViolationType violationType;

    @Column(name = "warning_threshold")
    private Double warningThreshold;

    @Column(name = "critical_threshold")
    private Double criticalThreshold;

    @Column(name = "required")
    private Boolean required;

    public void setPolicy(PerformancePolicy policy) {
        this.policy = policy;
    }

    public PerformanceViolationType getViolationType() { return violationType; }
    public void setViolationType(PerformanceViolationType v) { this.violationType = v; }
    public Double getWarningThreshold() { return warningThreshold; }
    public void setWarningThreshold(Double t) { this.warningThreshold = t; }
    public Double getCriticalThreshold() { return criticalThreshold; }
    public void setCriticalThreshold(Double t) { this.criticalThreshold = t; }
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean r) { this.required = r; }

    @Override
    protected String getPrefix() {
        return "PERF_THRESHOLD";
    }
}
