package com.handmade.ecommerce.policy.domain.aggregate;

import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import com.handmade.ecommerce.policy.domain.valueobject.PolicyStatus;
import org.chenile.stm.State;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;

/**
 * Platform-owned onboarding policy aggregate root
 * Uses state machine for lifecycle management (DRAFT → ACTIVE → DEPRECATED)
 * 
 * IMMUTABILITY: Once status = ACTIVE, audit fields cannot be changed
 * VERSIONING: Each policy has a unique version (e.g., "2024.1-US-INDIVIDUAL")
 * GOVERNANCE: Only ONE policy per (country, seller_type) can be ACTIVE at a time
 * 
 * State Machine Flow:
 * DRAFT --[approve]--> ACTIVE --[deprecate]--> DEPRECATED
 */
@Entity
@Table(name = "onboarding_policies",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"country_code", "seller_type", "policy_version"})
       },
       indexes = {
           @Index(name = "idx_active_policies", 
                  columnList = "country_code, seller_type, state_id, effective_date")
       })
public class OnboardingPolicy extends AbstractJpaStateEntity {


    
    /**
     * Globally unique version identifier
     * Format: "YYYY.N-COUNTRY-TYPE" (e.g., "2024.1-US-INDIVIDUAL")
     */
    @Column(name = "policy_version", length = 50, nullable = false, unique = true)
    private String policyVersion;
    
    /**
     * ISO 3166-1 alpha-2 country code
     */
    @Column(name = "country_code", length = 2, nullable = false)
    private String countryCode;
    
    /**
     * Seller type this policy applies to
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "seller_type", length = 50, nullable = false)
    private SellerType sellerType;
    
    /**
     * Date when this policy becomes active
     * Supports future-dated policies
     */
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;
    
    /**
     * Date when this policy was deprecated
     * NULL if still active
     */
    @Column(name = "deprecated_date")
    private LocalDate deprecatedDate;
    
    // ========== VERIFICATION REQUIREMENTS ==========
    
    @Column(name = "identity_verification_required")
    private boolean identityVerificationRequired;
    
    @Column(name = "tax_verification_required")
    private boolean taxVerificationRequired;
    
    @Column(name = "bank_verification_required")
    private boolean bankVerificationRequired;
    
    @Column(name = "manual_approval_required")
    private boolean manualApprovalRequired;
    
    // ========== AUDIT FIELDS (Immutable after ACTIVE) ==========
    
    /**
     * Admin user who created this policy
     */
    @Column(name = "created_by", length = 255, nullable = false)
    private String createdBy;
    
    /**
     * When this policy was created
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    /**
     * Compliance officer who approved this policy
     * Required before policy can be ACTIVE
     */
    @Column(name = "approved_by", length = 255)
    private String approvedBy;
    
    /**
     * When this policy was approved
     */
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OnboardingPolicyRule> rules = new ArrayList<>();
    
    // ========== METADATA ==========
    
    /**
     * Human-readable description of this policy
     */
    @Lob
    @Column(name = "description")
    private String description;
    
    /**
     * Legal/regulatory basis for this policy
     * Examples: "IRS 1099-K requirement", "EU AML Directive 5"
     */
    @Lob
    @Column(name = "regulatory_basis")
    private String regulatoryBasis;
    
    // ========== BUSINESS LOGIC ==========
    
    /**
     * Check if this policy is currently active
     */
    public boolean isActive() {
        return "ACTIVE".equals(getCurrentState().getStateId()) && 
               effectiveDate != null && 
               !effectiveDate.isAfter(LocalDate.now());
    }
    
    /**
     * Approve this policy (requires compliance officer)
     */
    public void approve(String complianceOfficerId) {
        if (!"DRAFT".equals(getCurrentState().getStateId())) {
            throw new IllegalStateException("Only DRAFT policies can be approved");
        }
        this.approvedBy = complianceOfficerId;
        this.approvedAt = LocalDateTime.now();
        // State transition is normally handled by STM, but keeping this for now
        // if manually called. In Chenile, you'd usually trigger an event.
    }
    
    /**
     * Deprecate this policy (no longer used for new sellers)
     */
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
    
    public SellerType getSellerType() {
        return sellerType;
    }
    
    public void setSellerType(SellerType sellerType) {
        this.sellerType = sellerType;
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

    public boolean isIdentityVerificationRequired() {
        return identityVerificationRequired;
    }

    public void setIdentityVerificationRequired(boolean identityVerificationRequired) {
        this.identityVerificationRequired = identityVerificationRequired;
    }

    public boolean isTaxVerificationRequired() {
        return taxVerificationRequired;
    }

    public void setTaxVerificationRequired(boolean taxVerificationRequired) {
        this.taxVerificationRequired = taxVerificationRequired;
    }

    public boolean isBankVerificationRequired() {
        return bankVerificationRequired;
    }

    public void setBankVerificationRequired(boolean bankVerificationRequired) {
        this.bankVerificationRequired = bankVerificationRequired;
    }

    public boolean isManualApprovalRequired() {
        return manualApprovalRequired;
    }

    public void setManualApprovalRequired(boolean manualApprovalRequired) {
        this.manualApprovalRequired = manualApprovalRequired;
    }
    
    public String getRegulatoryBasis() {
        return regulatoryBasis;
    }

    public void setRegulatoryBasis(String regulatoryBasis) {
        this.regulatoryBasis = regulatoryBasis;
    }

    public List<OnboardingPolicyRule> getRules() {
        return rules;
    }

    public void setRules(List<OnboardingPolicyRule> rules) {
        this.rules = rules;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PolicyStatus getStatus() {
        State state = this.getCurrentState();
        if (state == null || state.getStateId() == null) return null;
        try {
            return PolicyStatus.valueOf(state.getStateId());
        } catch (Exception e) {
            return null;
        }
    }

    public void setStatus(PolicyStatus status) {
        if (status != null) {
            State state = this.getCurrentState();
            if (state == null) {
                state = new State(status.name(), null);
                this.setCurrentState(state);
            } else {
                state.setStateId(status.name());
            }
        }
    }
}
