package com.handmade.ecommerce.payout.domain.aggregate;

import com.handmade.ecommerce.payout.domain.valueobject.PayoutFrequency;
import com.handmade.ecommerce.seller.domain.enums.SellerType;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Platform-owned payout policy aggregate root
 * Uses state machine for lifecycle management (DRAFT → ACTIVE → DEPRECATED)
 * 
 * IMMUTABILITY: Once status = ACTIVE, core fields cannot be changed
 * VERSIONING: Each policy has unique version (e.g., "2024.1-US-INDIVIDUAL")
 * GOVERNANCE: Only ONE policy per (country, seller_type) can be ACTIVE at a time
 * 
 * State Machine Flow:
 * DRAFT --[approve]--> ACTIVE --[deprecate]--> DEPRECATED
 */
@Entity
@Table(name = "payout_policies",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"country_code", "seller_type", "version"})
       },
       indexes = {
           @Index(name = "idx_active_payout_policies", 
                  columnList = "country_code, seller_type, state_id, effective_from"),
           @Index(name = "idx_payout_policy_version", 
                  columnList = "version")
       })
public class PayoutPolicy extends AbstractJpaStateEntity {
    
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
     * NULL = no end date
     */
    @Column(name = "effective_to")
    private LocalDate effectiveTo;
    
    /**
     * Date when this policy was deprecated
     */
    @Column(name = "deprecated_date")
    private LocalDate deprecatedDate;
    
    // ========== PAYOUT CONFIGURATION ==========
    
    /**
     * Default payout frequency
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "default_frequency", length = 20, nullable = false)
    private PayoutFrequency defaultFrequency;
    
    /**
     * Minimum payout threshold
     */
    @Column(name = "minimum_threshold", precision = 19, scale = 4, nullable = false)
    private BigDecimal minimumThreshold;
    
    /**
     * Currency code (ISO 4217)
     */
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;
    
    /**
     * Hold period in days before first payout
     */
    @Column(name = "hold_period_days", nullable = false)
    private Integer holdPeriodDays;
    
    /**
     * Rolling reserve percentage (0-100)
     * Platform holds this % for risk mitigation
     */
    @Column(name = "rolling_reserve_percentage", precision = 5, scale = 2)
    private BigDecimal rollingReservePercentage;
    
    /**
     * Reserve release days
     * Days after which rolling reserve is released
     */
    @Column(name = "reserve_release_days")
    private Integer reserveReleaseDays;
    
    // ========== AUDIT FIELDS ==========
    
    @Column(name = "created_by", length = 255, nullable = false)
    private String createdBy;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "approved_by", length = 255)
    private String approvedBy;
    
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "regulatory_basis", columnDefinition = "TEXT")
    private String regulatoryBasis;
    
    // ========== BUSINESS LOGIC ==========
    
    /**
     * Check if this policy is currently active
     */
    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return "ACTIVE".equals(getStateId())
            && effectiveFrom != null 
            && !today.isBefore(effectiveFrom)
            && (effectiveTo == null || !today.isAfter(effectiveTo));
    }
    
    /**
     * Check if policy can be modified
     * Only DRAFT policies can be modified
     */
    public boolean canModify() {
        return "DRAFT".equals(getStateId());
    }
    
    /**
     * Check if policy is deprecated
     */
    public boolean isDeprecated() {
        return "DEPRECATED".equals(getStateId());
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
    
    public PayoutFrequency getDefaultFrequency() {
        return defaultFrequency;
    }
    
    public void setDefaultFrequency(PayoutFrequency defaultFrequency) {
        this.defaultFrequency = defaultFrequency;
    }
    
    public BigDecimal getMinimumThreshold() {
        return minimumThreshold;
    }
    
    public void setMinimumThreshold(BigDecimal minimumThreshold) {
        this.minimumThreshold = minimumThreshold;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public Integer getHoldPeriodDays() {
        return holdPeriodDays;
    }
    
    public void setHoldPeriodDays(Integer holdPeriodDays) {
        this.holdPeriodDays = holdPeriodDays;
    }
    
    public BigDecimal getRollingReservePercentage() {
        return rollingReservePercentage;
    }
    
    public void setRollingReservePercentage(BigDecimal rollingReservePercentage) {
        this.rollingReservePercentage = rollingReservePercentage;
    }
    
    public Integer getReserveReleaseDays() {
        return reserveReleaseDays;
    }
    
    public void setReserveReleaseDays(Integer reserveReleaseDays) {
        this.reserveReleaseDays = reserveReleaseDays;
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
