package com.handmade.ecommerce.platform.domain.policy;

import com.handmade.ecommerce.seller.domain.enums.SellerType;
import org.chenile.jpautils.entity.BaseJpaEntity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Platform-owned onboarding policy
 * Defines what verification steps are required for a specific jurisdiction and seller type
 * 
 * IMMUTABILITY: Once status = ACTIVE, audit fields cannot be changed
 * VERSIONING: Each policy has a unique version (e.g., "2024.1-US-INDIVIDUAL")
 * GOVERNANCE: Only ONE policy per (country, seller_type) can be ACTIVE at a time
 */
@Entity
@Table(name = "onboarding_policies",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"country_code", "seller_type", "policy_version"})
       },
       indexes = {
           @Index(name = "idx_active_policies", 
                  columnList = "country_code, seller_type, status, effective_date")
       })
public class OnboardingPolicy extends BaseJpaEntity {
    
    @Id
    @Column(name = "id", length = 255)
    private String id;
    
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
     * Policy lifecycle status
     * DRAFT: Being created, not yet active
     * ACTIVE: Currently in use for new sellers
     * DEPRECATED: No longer used for new sellers (existing sellers grandfathered)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private PolicyStatus status;
    
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
    
    // ========== METADATA ==========
    
    /**
     * Human-readable description of this policy
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    /**
     * Legal/regulatory basis for this policy
     * Examples: "IRS 1099-K requirement", "EU AML Directive 5"
     */
    @Column(name = "regulatory_basis", columnDefinition = "TEXT")
    private String regulatoryBasis;
    
    // ========== BUSINESS LOGIC ==========
    
    /**
     * Check if this policy is currently active
     */
    public boolean isActive() {
        return status == PolicyStatus.ACTIVE && 
               effectiveDate != null && 
               !effectiveDate.isAfter(LocalDate.now());
    }
    
    /**
     * Approve this policy (requires compliance officer)
     */
    public void approve(String complianceOfficerId) {
        if (this.status != PolicyStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT policies can be approved");
        }
        this.approvedBy = complianceOfficerId;
        this.approvedAt = LocalDateTime.now();
        this.status = PolicyStatus.ACTIVE;
    }
    
    /**
     * Deprecate this policy (no longer used for new sellers)
     */
    public void deprecate() {
        if (this.status != PolicyStatus.ACTIVE) {
            throw new IllegalStateException("Only ACTIVE policies can be deprecated");
        }
        this.status = PolicyStatus.DEPRECATED;
        this.deprecatedDate = LocalDate.now();
    }
    
    // ========== GETTERS AND SETTERS ==========
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
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
    
    public PolicyStatus getStatus() {
        return status;
    }
    
    public void setStatus(PolicyStatus status) {
        this.status = status;
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getRegulatoryBasis() {
        return regulatoryBasis;
    }
    
    public void setRegulatoryBasis(String regulatoryBasis) {
        this.regulatoryBasis = regulatoryBasis;
    }
}
