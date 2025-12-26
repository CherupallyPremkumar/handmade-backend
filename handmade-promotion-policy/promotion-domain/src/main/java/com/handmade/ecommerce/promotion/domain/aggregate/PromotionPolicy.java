package com.handmade.ecommerce.promotion.domain.aggregate;

import com.handmade.ecommerce.promotion.domain.valueobject.DiscountType;
import com.handmade.ecommerce.seller.domain.enums.SellerType;
import org.chenile.stm.model.AbstractJpaStateEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Platform-owned promotion policy aggregate root
 * Uses state machine for lifecycle management (DRAFT → ACTIVE → DEPRECATED)
 * 
 * Defines platform-wide promotional campaigns and seller eligibility
 * Examples: Black Friday, New Seller Boost, Category Promotions
 * 
 * State Machine Flow:
 * DRAFT --[approve]--> ACTIVE --[deprecate]--> DEPRECATED
 */
@Entity
@Table(name = "promotion_policies",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"country_code", "seller_type", "version"})
       },
       indexes = {
           @Index(name = "idx_active_promotion_policies", 
                  columnList = "country_code, seller_type, state_id, effective_from")
       })
public class PromotionPolicy extends AbstractJpaStateEntity {
    
    /**
     * Globally unique version identifier
     * Format: "YYYY.N-COUNTRY-TYPE" (e.g., "2024.1-US-INDIVIDUAL")
     */
    @Column(name = "version", length = 50, nullable = false, unique = true)
    private String version;
    
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
    
    // ========== PROMOTION CONFIGURATION ==========
    
    /**
     * Promotion name
     */
    @Column(name = "promotion_name", length = 255, nullable = false)
    private String promotionName;
    
    /**
     * Default discount type
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "default_discount_type", length = 50, nullable = false)
    private DiscountType defaultDiscountType;
    
    /**
     * Maximum discount percentage (0-100)
     */
    @Column(name = "max_discount_percentage", precision = 5, scale = 2)
    private BigDecimal maxDiscountPercentage;
    
    /**
     * Maximum discount amount
     */
    @Column(name = "max_discount_amount", precision = 19, scale = 4)
    private BigDecimal maxDiscountAmount;
    
    /**
     * Platform co-funding percentage (platform pays this %)
     */
    @Column(name = "platform_co_funding_percentage", precision = 5, scale = 2)
    private BigDecimal platformCoFundingPercentage;
    
    /**
     * Minimum order value to qualify
     */
    @Column(name = "minimum_order_value", precision = 19, scale = 4)
    private BigDecimal minimumOrderValue;
    
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
    
    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return "ACTIVE".equals(getStateId())
            && effectiveFrom != null 
            && !today.isBefore(effectiveFrom)
            && (effectiveTo == null || !today.isAfter(effectiveTo));
    }
    
    public boolean canModify() {
        return "DRAFT".equals(getStateId());
    }
    
    public boolean isDeprecated() {
        return "DEPRECATED".equals(getStateId());
    }
    
    // ========== GETTERS AND SETTERS ==========
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
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
    
    public String getPromotionName() {
        return promotionName;
    }
    
    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }
    
    public DiscountType getDefaultDiscountType() {
        return defaultDiscountType;
    }
    
    public void setDefaultDiscountType(DiscountType defaultDiscountType) {
        this.defaultDiscountType = defaultDiscountType;
    }
    
    public BigDecimal getMaxDiscountPercentage() {
        return maxDiscountPercentage;
    }
    
    public void setMaxDiscountPercentage(BigDecimal maxDiscountPercentage) {
        this.maxDiscountPercentage = maxDiscountPercentage;
    }
    
    public BigDecimal getMaxDiscountAmount() {
        return maxDiscountAmount;
    }
    
    public void setMaxDiscountAmount(BigDecimal maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }
    
    public BigDecimal getPlatformCoFundingPercentage() {
        return platformCoFundingPercentage;
    }
    
    public void setPlatformCoFundingPercentage(BigDecimal platformCoFundingPercentage) {
        this.platformCoFundingPercentage = platformCoFundingPercentage;
    }
    
    public BigDecimal getMinimumOrderValue() {
        return minimumOrderValue;
    }
    
    public void setMinimumOrderValue(BigDecimal minimumOrderValue) {
        this.minimumOrderValue = minimumOrderValue;
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
