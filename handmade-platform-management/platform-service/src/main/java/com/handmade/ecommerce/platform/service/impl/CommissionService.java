package com.handmade.ecommerce.platform.service.impl;

import com.handmade.ecommerce.platform.domain.config.model.CommissionConfig;
import com.handmade.ecommerce.platform.domain.config.reader.CommissionConfigurator;
import com.handmade.ecommerce.platform.domain.valueobject.SellerTier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;


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
        CommissionConfig.PolicyConfig policy = commissionConfigurator.getActivePolicy();
        if (policy == null) {
            throw new IllegalStateException("No active commission policy found");
        }
        BigDecimal tierRate = getTierRate(policy, sellerTier);
        BigDecimal effectiveRate = applyVolumeDiscount(policy, tierRate, monthlyVolume);
        return orderAmount.multiply(effectiveRate)
            .setScale(2, RoundingMode.HALF_UP);
    }

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
    public BigDecimal calculateTotalFees(BigDecimal orderAmount, SellerTier sellerTier, BigDecimal monthlyVolume) {
        BigDecimal commission = calculateCommission(orderAmount, sellerTier, monthlyVolume);
        BigDecimal processingFee = calculateProcessingFee(orderAmount);
        return commission.add(processingFee);
    }
    private BigDecimal getTierRate(CommissionConfig.PolicyConfig policy, SellerTier sellerTier) {
        if (sellerTier == null || policy.tierRates == null) {
            return policy.baseCommissionRate;
        }
        return policy.tierRates.getOrDefault(sellerTier, policy.baseCommissionRate);
    }

    private BigDecimal applyVolumeDiscount(CommissionConfig.PolicyConfig policy, BigDecimal rate, BigDecimal monthlyVolume) {
        if (monthlyVolume == null || policy.volumeDiscounts == null || policy.volumeDiscounts.isEmpty()) {
            return rate;
        }

        BigDecimal discount = BigDecimal.ZERO;
        for (CommissionConfig.VolumeDiscount volumeDiscount : policy.volumeDiscounts) {
            if (monthlyVolume.compareTo(volumeDiscount.threshold) >= 0) {
                discount = volumeDiscount.discountRate;
            }
        }
        return rate.subtract(discount).max(BigDecimal.ZERO);
    }
}
