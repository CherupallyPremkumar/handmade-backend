package com.handmade.ecommerce.platform.domain.aggregate;

import org.chenile.jpautils.entity.BaseJpaEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Commission Policy Aggregate Root
 * Encapsulates all commission calculation logic for the platform
 * Follows DDD: Business logic lives in the domain model
 */
@Entity
@Table(name = "commission_policy")
public class CommissionPolicy extends BaseJpaEntity {
    
    // Policy Identity
    @Column(name = "policy_name", nullable = false)
    public String policyName;
    
    // Base Commission Rates
    @Column(name = "base_commission_rate", precision = 5, scale = 4)
    public BigDecimal baseCommissionRate; // e.g., 0.1000 = 10%
    
    @Column(name = "processing_fee_rate", precision = 5, scale = 4)
    public BigDecimal processingFeeRate; // e.g., 0.0250 = 2.5%
    
    // Tier-based Rates
    @Column(name = "artisan_rate", precision = 5, scale = 4)
    public BigDecimal artisanRate; // 10% for individual artisans
    
    @Column(name = "home_maker_rate", precision = 5, scale = 4)
    public BigDecimal homeMakerRate; // 8% for home-based makers
    
    @Column(name = "small_business_rate", precision = 5, scale = 4)
    public BigDecimal smallBusinessRate; // 6% for small businesses
    
    @Column(name = "enterprise_rate", precision = 5, scale = 4)
    public BigDecimal enterpriseRate; // 5% for enterprises
    
    // Volume-based Discounts
    @Column(name = "high_volume_threshold", precision = 15, scale = 2)
    public BigDecimal highVolumeThreshold; // e.g., 100,000
    
    @Column(name = "high_volume_discount", precision = 5, scale = 4)
    public BigDecimal highVolumeDiscount; // e.g., 0.01 = 1% discount
    
    // Validity Period
    @Column(name = "effective_from")
    public LocalDateTime effectiveFrom;
    
    @Column(name = "effective_to")
    public LocalDateTime effectiveTo;
    
    @Column(name = "is_active")
    public boolean isActive = true;
    
    /**
     * JPA Constructor
     */
    public CommissionPolicy() {
    }
    
    /**
     * Factory method to create default policy
     */
    public static CommissionPolicy createDefault() {
        CommissionPolicy policy = new CommissionPolicy();
        policy.policyName = "Default Commission Policy";
        policy.baseCommissionRate = new BigDecimal("0.0800"); // 8%
        policy.processingFeeRate = new BigDecimal("0.0250"); // 2.5%
        policy.artisanRate = new BigDecimal("0.1000"); // 10%
        policy.homeMakerRate = new BigDecimal("0.0800"); // 8%
        policy.smallBusinessRate = new BigDecimal("0.0600"); // 6%
        policy.enterpriseRate = new BigDecimal("0.0500"); // 5%
        policy.highVolumeThreshold = new BigDecimal("100000.00");
        policy.highVolumeDiscount = new BigDecimal("0.0100"); // 1%
        policy.effectiveFrom = LocalDateTime.now();
        policy.isActive = true;
        return policy;
    }
    
    /**
     * BUSINESS LOGIC: Calculate commission for an order
     * This is the core domain logic
     */
    public BigDecimal calculateCommission(BigDecimal orderAmount, String sellerTier, BigDecimal monthlyVolume) {
        if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        // 1. Get tier-based rate
        BigDecimal tierRate = getTierRate(sellerTier);
        
        // 2. Apply volume discount if applicable
        BigDecimal effectiveRate = applyVolumeDiscount(tierRate, monthlyVolume);
        
        // 3. Calculate commission
        BigDecimal commission = orderAmount.multiply(effectiveRate)
            .setScale(2, RoundingMode.HALF_UP);
        
        return commission;
    }
    
    /**
     * Calculate processing fee (separate from commission)
     */
    public BigDecimal calculateProcessingFee(BigDecimal orderAmount) {
        if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        return orderAmount.multiply(processingFeeRate)
            .setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculate total platform fees (commission + processing fee)
     */
    public BigDecimal calculateTotalFees(BigDecimal orderAmount, String sellerTier, BigDecimal monthlyVolume) {
        BigDecimal commission = calculateCommission(orderAmount, sellerTier, monthlyVolume);
        BigDecimal processingFee = calculateProcessingFee(orderAmount);
        return commission.add(processingFee);
    }
    
    /**
     * Get commission rate based on seller tier
     */
    private BigDecimal getTierRate(String sellerTier) {
        if (sellerTier == null) {
            return baseCommissionRate;
        }
        
        return switch (sellerTier.toUpperCase()) {
            case "ARTISAN", "INDIVIDUAL_ARTISAN" -> artisanRate;
            case "HOME_MAKER", "HOME_BASED_MAKER" -> homeMakerRate;
            case "SMALL_BUSINESS" -> smallBusinessRate;
            case "ENTERPRISE", "ENTERPRISE_SELLER" -> enterpriseRate;
            default -> baseCommissionRate;
        };
    }
    
    /**
     * Apply volume-based discount
     */
    private BigDecimal applyVolumeDiscount(BigDecimal rate, BigDecimal monthlyVolume) {
        if (monthlyVolume == null || highVolumeThreshold == null) {
            return rate;
        }
        
        // If seller exceeds high volume threshold, apply discount
        if (monthlyVolume.compareTo(highVolumeThreshold) >= 0) {
            return rate.subtract(highVolumeDiscount).max(BigDecimal.ZERO);
        }
        
        return rate;
    }
    
    /**
     * Check if policy is currently valid
     */
    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        
        if (!isActive) {
            return false;
        }
        
        if (effectiveFrom != null && now.isBefore(effectiveFrom)) {
            return false;
        }
        
        if (effectiveTo != null && now.isAfter(effectiveTo)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Override getPrefix for ID generation
     */
    @Override
    protected String getPrefix() {
        return "COMM_POLICY";
    }
}
