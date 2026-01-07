package com.handmade.ecommerce.seller.domain.valueobject;

import com.handmade.ecommerce.seller.domain.enums.SellerTier;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Seller performance metrics (Amazon-style)
 * Used for tier classification and auto-suspension
 */
@Embeddable
public class SellerMetrics implements Serializable {

    // Sales metrics
    private Integer totalSales;
    private BigDecimal totalRevenue;
    private BigDecimal averageOrderValue;
    private BigDecimal monthlyRevenue; // Last 30 days

    // Performance metrics (Amazon-style thresholds)
    private BigDecimal orderDefectRate; // Target: < 1%
    private BigDecimal lateShipmentRate; // Target: < 4%
    private BigDecimal cancellationRate; // Target: < 2.5%
    private BigDecimal customerRating; // 0-5 stars
    private Integer totalReviews;

    private boolean hasPerformanceIssues;
    private LocalDateTime lastPerformanceIssue;

    // Tier management
    @Enumerated(EnumType.STRING)
    private SellerTier tier;

    // Timestamps
    private LocalDateTime lastOrderDate;
    private LocalDateTime joinedDate;
    private LocalDateTime lastMetricsUpdate;

    public SellerMetrics() {
        this.totalSales = 0;
        this.totalRevenue = BigDecimal.ZERO;
        this.averageOrderValue = BigDecimal.ZERO;
        this.monthlyRevenue = BigDecimal.ZERO;
        this.orderDefectRate = BigDecimal.ZERO;
        this.lateShipmentRate = BigDecimal.ZERO;
        this.cancellationRate = BigDecimal.ZERO;
        this.customerRating = BigDecimal.ZERO;
        this.totalReviews = 0;
        this.tier = SellerTier.BRONZE;
        this.joinedDate = LocalDateTime.now();
    }

    /**
     * Check if seller should be auto-suspended based on metrics
     */
    public boolean requiresAutoSuspension() {
        return orderDefectRate.compareTo(new BigDecimal("0.01")) > 0 // > 1%
                || lateShipmentRate.compareTo(new BigDecimal("0.04")) > 0 // > 4%
                || (customerRating.compareTo(new BigDecimal("3.0")) < 0 && totalReviews > 10); // < 3.0 stars with
                                                                                               // reviews
    }

    /**
     * Calculate seller tier based on monthly revenue
     */
    public SellerTier calculateTier() {
        if (monthlyRevenue.compareTo(new BigDecimal("250000")) >= 0) {
            return SellerTier.PLATINUM;
        } else if (monthlyRevenue.compareTo(new BigDecimal("50000")) >= 0) {
            return SellerTier.GOLD;
        } else if (monthlyRevenue.compareTo(new BigDecimal("10000")) >= 0) {
            return SellerTier.SILVER;
        }
        return SellerTier.BRONZE;
    }

    // Getters and Setters
    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getAverageOrderValue() {
        return averageOrderValue;
    }

    public void setAverageOrderValue(BigDecimal averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }

    public BigDecimal getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(BigDecimal monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public BigDecimal getOrderDefectRate() {
        return orderDefectRate;
    }

    public void setOrderDefectRate(BigDecimal orderDefectRate) {
        this.orderDefectRate = orderDefectRate;
    }

    public BigDecimal getLateShipmentRate() {
        return lateShipmentRate;
    }

    public void setLateShipmentRate(BigDecimal lateShipmentRate) {
        this.lateShipmentRate = lateShipmentRate;
    }

    public BigDecimal getCancellationRate() {
        return cancellationRate;
    }

    public void setCancellationRate(BigDecimal cancellationRate) {
        this.cancellationRate = cancellationRate;
    }

    public BigDecimal getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(BigDecimal customerRating) {
        this.customerRating = customerRating;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public SellerTier getTier() {
        return tier;
    }

    public void setTier(SellerTier tier) {
        this.tier = tier;
    }

    public LocalDateTime getLastOrderDate() {
        return lastOrderDate;
    }

    public void setLastOrderDate(LocalDateTime lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }

    public LocalDateTime getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDateTime joinedDate) {
        this.joinedDate = joinedDate;
    }

    public LocalDateTime getLastMetricsUpdate() {
        return lastMetricsUpdate;
    }

    public void setLastMetricsUpdate(LocalDateTime lastMetricsUpdate) {
        this.lastMetricsUpdate = lastMetricsUpdate;
    }

    public boolean isHasPerformanceIssues() {
        return hasPerformanceIssues;
    }

    public void setHasPerformanceIssues(boolean hasPerformanceIssues) {
        this.hasPerformanceIssues = hasPerformanceIssues;
    }

    public LocalDateTime getLastPerformanceIssue() {
        return lastPerformanceIssue;
    }

    public void setLastPerformanceIssue(LocalDateTime lastPerformanceIssue) {
        this.lastPerformanceIssue = lastPerformanceIssue;
    }
}
