package com.handmade.ecommerce.compliance.domain.aggregate;

import com.handmade.ecommerce.compliance.domain.valueobject.ComplianceSeverity;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Platform-owned compliance policy aggregate root
 * Uses state machine for lifecycle management (DRAFT → ACTIVE → DEPRECATED)
 * 
 * Defines regulatory and legal requirements for sellers
 * Examples: AML checks, tax compliance, data protection
 * 
 * State Machine Flow:
 * DRAFT --[approve]--> ACTIVE --[deprecate]--> DEPRECATED
 */
@Entity
@Table(name = "compliance_policies", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "country_code", "seller_type", "version" })
}, indexes = {
        @Index(name = "idx_active_compliance_policies", columnList = "country_code, seller_type, state_id, effective_from")
})
public class CompliancePolicy extends AbstractJpaStateEntity {

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
     */
    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    /**
     * Date when this policy stops being active
     */
    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    /**
     * Date when this policy was deprecated
     */
    @Column(name = "deprecated_date")
    private LocalDate deprecatedDate;

    // ========== COMPLIANCE CONFIGURATION ==========

    /**
     * Minimum compliance score required (0-100)
     */
    @Column(name = "minimum_compliance_score", nullable = false)
    private Integer minimumComplianceScore;

    /**
     * Requires annual compliance review
     */
    @Column(name = "requires_annual_review", nullable = false)
    private Boolean requiresAnnualReview;

    /**
     * AML (Anti-Money Laundering) check required
     */
    @Column(name = "requires_aml_check", nullable = false)
    private Boolean requiresAmlCheck;

    /**
     * Data protection compliance required (GDPR, CCPA, etc.)
     */
    @Column(name = "requires_data_protection", nullable = false)
    private Boolean requiresDataProtection;

    // ========== AUDIT FIELDS ==========

    @Column(name = "created_by", length = 255, nullable = false)
    private String createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "approved_by", length = 255)
    private String approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "regulatory_basis")
    private String regulatoryBasis;

    // ========== BUSINESS LOGIC ==========

    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return "ACTIVE".equals(getCurrentState().getStateId())
                && effectiveFrom != null
                && !today.isBefore(effectiveFrom)
                && (effectiveTo == null || !today.isAfter(effectiveTo));
    }

    public boolean canModify() {
        return "DRAFT".equals(getCurrentState().getStateId());
    }

    public boolean isDeprecated() {
        return "DEPRECATED".equals(getCurrentState().getStateId());
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

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public LocalDate getDeprecatedDate() {
        return deprecatedDate;
    }

    public void setDeprecatedDate(LocalDate deprecatedDate) {
        this.deprecatedDate = deprecatedDate;
    }

    public Integer getMinimumComplianceScore() {
        return minimumComplianceScore;
    }

    public void setMinimumComplianceScore(Integer minimumComplianceScore) {
        this.minimumComplianceScore = minimumComplianceScore;
    }

    public Boolean getRequiresAnnualReview() {
        return requiresAnnualReview;
    }

    public void setRequiresAnnualReview(Boolean requiresAnnualReview) {
        this.requiresAnnualReview = requiresAnnualReview;
    }

    public Boolean getRequiresAmlCheck() {
        return requiresAmlCheck;
    }

    public void setRequiresAmlCheck(Boolean requiresAmlCheck) {
        this.requiresAmlCheck = requiresAmlCheck;
    }

    public Boolean getRequiresDataProtection() {
        return requiresDataProtection;
    }

    public void setRequiresDataProtection(Boolean requiresDataProtection) {
        this.requiresDataProtection = requiresDataProtection;
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
