package com.handmade.ecommerce.policy.domain.aggregate;

import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import com.handmade.ecommerce.policy.domain.valueobject.PolicyStatus;
import org.chenile.stm.State;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.handmade.ecommerce.policy.domain.entity.PricingPolicyRule;

/**
 * Platform-owned pricing policy aggregate root
 * Uses state machine for lifecycle management (DRAFT → ACTIVE → DEPRECATED)
 * 
 * IMMUTABILITY: Once status = ACTIVE, core rules cannot be changed
 * VERSIONING: Each policy has a unique version (e.g., "2024.1-US-ELECTRONICS")
 * GOVERNANCE: Only ONE policy per (country, category) can be ACTIVE at a time
 * 
 * State Machine Flow:
 * DRAFT --[approve]-> ACTIVE --[deprecate]-> DEPRECATED
 */
@Entity
@Table(name = "pricing_policies",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"country_code", "category", "policy_version"})
       },
       indexes = {
           @Index(name = "idx_active_pricing_policies", 
                  columnList = "country_code, category, state_id, effective_date")
       })
public class PricingPolicy extends AbstractJpaStateEntity {

    /**
     * Globally unique version identifier
     * Format: "YYYY.N-COUNTRY-CATEGORY" (e.g., "2024.1-US-ELECTRONICS")
     */
    @Column(name = "policy_version", length = 50, nullable = false, unique = true)
    private String policyVersion;
    
    /**
     * ISO 3166-1 alpha-2 country code
     */
    @Column(name = "country_code", length = 2, nullable = false)
    private String countryCode;
    
    /**
     * Product category this policy applies to
     * NULL = applies to all categories
     */
    @Column(name = "category", length = 100)
    private String category;
    
    /**
     * Date when this policy becomes active
     */
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;
    
    /**
     * Date when this policy was deprecated
     */
    @Column(name = "deprecated_date")
    private LocalDate deprecatedDate;
    
    // ========== PRICING THRESHOLDS ==========
    
    /**
     * Minimum allowed price (in cents)
     * NULL = no minimum
     */
    @Column(name = "min_price_cents")
    private Long minPriceCents;
    
    /**
     * Maximum allowed price (in cents)
     * NULL = no maximum
     */
    @Column(name = "max_price_cents")
    private Long maxPriceCents;
    
    /**
     * Maximum markup percentage allowed
     * Example: 300 = 300% markup
     */
    @Column(name = "max_markup_percentage")
    private Integer maxMarkupPercentage;
    
    /**
     * Maximum price changes allowed per day
     */
    @Column(name = "max_price_changes_per_day")
    private Integer maxPriceChangesPerDay = 3;
    
    /**
     * Minimum hours between price changes
     */
    @Column(name = "min_hours_between_changes")
    private Integer minHoursBetweenChanges = 24;
    
    /**
     * Enable price gouging detection
     */
    @Column(name = "price_gouging_detection_enabled")
    private boolean priceGougingDetectionEnabled = true;
    
    /**
     * Manual review required for prices above this threshold (in cents)
     */
    @Column(name = "manual_review_threshold_cents")
    private Long manualReviewThresholdCents;
    
    // ========== AUDIT FIELDS ==========
    
    @Column(name = "created_by", length = 255, nullable = false)
    private String createdBy;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "approved_by", length = 255)
    private String approvedBy;
    
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PricingPolicyRule> rules = new ArrayList<>();
    
    // ========== METADATA ==========
    
    @Lob
    @Column(name = "description")
    private String description;
    
    @Lob
    @Column(name = "regulatory_basis")
    private String regulatoryBasis;
    
    // ========== BUSINESS LOGIC ==========
    
    public boolean isActive() {
        return "ACTIVE".equals(getCurrentState().getStateId()) && 
               effectiveDate != null && 
               !effectiveDate.isAfter(LocalDate.now());
    }
    
    public void approve(String complianceOfficerId) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Only DRAFT policies can be approved");
        }
        this.approvedBy = complianceOfficerId;
        this.approvedAt = LocalDateTime.now();
    }
    
    public void deprecate() {
        if (!"ACTIVE".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Only ACTIVE policies can be deprecated");
        }
        this.deprecatedDate = LocalDate.now();
    }
    
    // ========== GETTERS AND SETTERS ==========
    
    public String getPolicyVersion() {
        return policyVersion;
    }
    
    public void setPolicyVersion(String policyVersion) {
        this.policyVersion = policyVersion;
    }
    
    public String getCountryCode() {
        return countryCode;
    }
    
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }
    
    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    public LocalDate getDeprecatedDate() {
        return deprecatedDate;
    }
    
    public void setDeprecatedDate(LocalDate deprecatedDate) {
        this.deprecatedDate = deprecatedDate;
    }
    
    public Long getMinPriceCents() {
        return minPriceCents;
    }
    
    public void setMinPriceCents(Long minPriceCents) {
        this.minPriceCents = minPriceCents;
    }
    
    public Long getMaxPriceCents() {
        return maxPriceCents;
    }
    
    public void setMaxPriceCents(Long maxPriceCents) {
        this.maxPriceCents = maxPriceCents;
    }
    
    public Integer getMaxMarkupPercentage() {
        return maxMarkupPercentage;
    }
    
    public void setMaxMarkupPercentage(Integer maxMarkupPercentage) {
        this.maxMarkupPercentage = maxMarkupPercentage;
    }
    
    public Integer getMaxPriceChangesPerDay() {
        return maxPriceChangesPerDay;
    }
    
    public void setMaxPriceChangesPerDay(Integer maxPriceChangesPerDay) {
        this.maxPriceChangesPerDay = maxPriceChangesPerDay;
    }
    
    public Integer getMinHoursBetweenChanges() {
        return minHoursBetweenChanges;
    }
    
    public void setMinHoursBetweenChanges(Integer minHoursBetweenChanges) {
        this.minHoursBetweenChanges = minHoursBetweenChanges;
    }
    
    public boolean isPriceGougingDetectionEnabled() {
        return priceGougingDetectionEnabled;
    }
    
    public void setPriceGougingDetectionEnabled(boolean priceGougingDetectionEnabled) {
        this.priceGougingDetectionEnabled = priceGougingDetectionEnabled;
    }
    
    public Long getManualReviewThresholdCents() {
        return manualReviewThresholdCents;
    }
    
    public void setManualReviewThresholdCents(Long manualReviewThresholdCents) {
        this.manualReviewThresholdCents = manualReviewThresholdCents;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getApprovedBy() {
        return approvedBy;
    }
    
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
    
    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }
    
    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public List<PricingPolicyRule> getRules() {
        return rules;
    }

    public void addRule(PricingPolicyRule rule) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Rules can only be added to DRAFT policies");
        }
        rule.setPolicy(this);
        this.rules.add(rule);
    }

    public void updateRule(PricingPolicyRule rule) {
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

    @Override
    protected String getPrefix() {
        return "PRICING_POLICY";
    }
}
