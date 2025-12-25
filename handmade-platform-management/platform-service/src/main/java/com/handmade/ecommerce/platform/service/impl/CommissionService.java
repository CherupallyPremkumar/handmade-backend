package com.handmade.ecommerce.platform.service;

import com.handmade.ecommerce.platform.domain.config.model.CommissionConfig;
import com.handmade.ecommerce.platform.domain.config.reader.CommissionConfigurator;
import com.handmade.ecommerce.platform.domain.valueobject.SellerTier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Commission Service
 * Uses JSON configuration (like chenile process) instead of database
 */
@Service
public class CommissionService {
    
    @Autowired
    private CommissionConfigurator commissionConfigurator;
    
    /**
     * Calculate commission using JSON configuration
     */
    public BigDecimal calculateCommission(BigDecimal orderAmount, SellerTier sellerTier, BigDecimal monthlyVolume) {
        if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        // Get active policy from JSON config
        CommissionConfig.PolicyConfig policy = commissionConfigurator.getActivePolicy();
        if (policy == null) {
            throw new IllegalStateException("No active commission policy found");
        }
        
        // 1. Get tier-based rate from JSON config
        BigDecimal tierRate = getTierRate(policy, sellerTier);
        
        // 2. Apply volume discount from JSON config
        BigDecimal effectiveRate = applyVolumeDiscount(policy, tierRate, monthlyVolume);
        
        // 3. Calculate commission
        return orderAmount.multiply(effectiveRate)
            .setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculate processing fee
     */
    public BigDecimal calculateProcessingFee(BigDecimal orderAmount) {
        if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        CommissionConfig.PolicyConfig policy = commissionConfigurator.getActivePolicy();
        if (policy == null) {
            throw new IllegalStateException("No active commission policy found");
        }
        
        return orderAmount.multiply(policy.processingFeeRate)
            .setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculate total fees
     */
    public BigDecimal calculateTotalFees(BigDecimal orderAmount, SellerTier sellerTier, BigDecimal monthlyVolume) {
        BigDecimal commission = calculateCommission(orderAmount, sellerTier, monthlyVolume);
        BigDecimal processingFee = calculateProcessingFee(orderAmount);
        return commission.add(processingFee);
    }
    
    /**
     * Get tier rate from JSON config
     */
    private BigDecimal getTierRate(CommissionConfig.PolicyConfig policy, SellerTier sellerTier) {
        if (sellerTier == null || policy.tierRates == null) {
            return policy.baseCommissionRate;
        }
        
        return policy.tierRates.getOrDefault(sellerTier, policy.baseCommissionRate);
    }
    
    /**
     * Apply volume discount from JSON config
     */
    private BigDecimal applyVolumeDiscount(CommissionConfig.PolicyConfig policy, BigDecimal rate, BigDecimal monthlyVolume) {
        if (monthlyVolume == null || policy.volumeDiscounts == null || policy.volumeDiscounts.isEmpty()) {
            return rate;
        }
        
        // Find applicable discount (highest threshold that seller meets)
        BigDecimal discount = BigDecimal.ZERO;
        for (CommissionConfig.VolumeDiscount volumeDiscount : policy.volumeDiscounts) {
            if (monthlyVolume.compareTo(volumeDiscount.threshold) >= 0) {
                discount = volumeDiscount.discountRate;
            }
        }
        
        return rate.subtract(discount).max(BigDecimal.ZERO);
    }
}
