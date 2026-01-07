package com.handmade.ecommerce.commission.domain.aggregate;

import com.handmade.ecommerce.commission.domain.entity.CommissionRule;
import com.handmade.ecommerce.commission.domain.valueobject.CommissionFeeType;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Commission Policy Aggregate Root
 * Uses state machine for lifecycle management (DRAFT → ACTIVE → DEPRECATED)
 * Standardized governance module for fee management
 */
@Entity
@Table(name = "commission_policies",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"country_code", "category", "policy_version"})
       },
       indexes = {
           @Index(name = "idx_active_comm_policies", 
                  columnList = "country_code, category, state_id, effective_date")
       })
public class CommissionPolicy extends AbstractJpaStateEntity {
    
    @Column(name = "policy_version", length = 50, nullable = false, unique = true)
    private String policyVersion;
    
    @Column(name = "country_code", length = 2, nullable = false)
    private String countryCode;
    
    @Column(name = "category", length = 100)
    private String category;
    
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;
    
    @Column(name = "deprecated_date")
    private LocalDate deprecatedDate;
    
    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CommissionRule> rules = new ArrayList<>();
    
    @Column(name = "is_active")
    private boolean isActive = true;

    // ========== RULE MANAGEMENT ==========

    public void addRule(CommissionRule rule) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Rules can only be added to DRAFT policies");
        }
        rule.setPolicy(this);
        this.rules.add(rule);
    }

    public void updateRule(CommissionRule rule) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Rules can only be updated in DRAFT policies");
        }
        this.rules.stream()
            .filter(r -> r.getRuleName().equals(rule.getRuleName()))
            .findFirst()
            .ifPresent(r -> {
                r.setFeeType(rule.getFeeType());
                r.setThresholdValue(rule.getThresholdValue());
                r.setRequired(rule.getRequired());
            });
    }

    public void removeRule(String ruleName) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Rules can only be removed from DRAFT policies");
        }
        this.rules.removeIf(r -> r.getRuleName().equals(ruleName));
    }

    // ========== BUSINESS LOGIC ==========

    /**
     * Calculate commission and fees for an order
     * Sums all applicable rules of specific fee types
     */
    public BigDecimal calculateTotalFees(BigDecimal orderAmount, CommissionFeeType feeType) {
        if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        return rules.stream()
            .filter(r -> r.getFeeType() == feeType && r.getRequired())
            .map(r -> orderAmount.multiply(r.getThresholdValue()))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(2, RoundingMode.HALF_UP);
    }

    // ========== GETTERS AND SETTERS ==========

    public String getPolicyVersion() { return policyVersion; }
    public void setPolicyVersion(String v) { this.policyVersion = v; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String c) { this.countryCode = c; }

    public String getCategory() { return category; }
    public void setCategory(String c) { this.category = c; }

    public LocalDate getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(LocalDate d) { this.effectiveDate = d; }

    public List<CommissionRule> getRules() { return rules; }

    @Override
    protected String getPrefix() {
        return "COMM_POLICY";
    }
}
