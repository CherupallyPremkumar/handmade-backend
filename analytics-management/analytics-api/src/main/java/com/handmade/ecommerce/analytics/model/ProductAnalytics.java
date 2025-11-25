package com.handmade.ecommerce.analytics.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ProductAnalytics - Aggregate Root
 * Tracks performance metrics for products
 */
@Entity
@Table(name = "product_analytics", indexes = {
    @Index(name = "idx_product_date", columnList = "product_id, analytics_date"),
    @Index(name = "idx_trending_score", columnList = "trending_score")
})
public class ProductAnalytics {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analytics_id")
    private Long analyticsId;
    
    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;
    
    @Column(name = "analytics_date", nullable = false)
    private LocalDate analyticsDate;
    
    @Column(name = "view_count")
    private Long viewCount = 0L;
    
    @Column(name = "unique_viewers")
    private Long uniqueViewers = 0L;
    
    @Column(name = "add_to_cart_count")
    private Long addToCartCount = 0L;
    
    @Column(name = "wishlist_count")
    private Long wishlistCount = 0L;
    
    @Column(name = "purchase_count")
    private Long purchaseCount = 0L;
    
    @Column(name = "revenue", precision = 10, scale = 2)
    private BigDecimal revenue = BigDecimal.ZERO;
    
    @Column(name = "conversion_rate")
    private Double conversionRate = 0.0;
    
    @Column(name = "trending_score")
    private Integer trendingScore = 0; // 0-100
    
    @Enumerated(EnumType.STRING)
    @Column(name = "trend", length = 10)
    private Trend trend;
    
    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;
    
    // Getters and Setters
    
    public Long getAnalyticsId() {
        return analyticsId;
    }
    
    public void setAnalyticsId(Long analyticsId) {
        this.analyticsId = analyticsId;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public LocalDate getAnalyticsDate() {
        return analyticsDate;
    }
    
    public void setAnalyticsDate(LocalDate analyticsDate) {
        this.analyticsDate = analyticsDate;
    }
    
    public Long getViewCount() {
        return viewCount;
    }
    
    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
    
    public Long getUniqueViewers() {
        return uniqueViewers;
    }
    
    public void setUniqueViewers(Long uniqueViewers) {
        this.uniqueViewers = uniqueViewers;
    }
    
    public Long getAddToCartCount() {
        return addToCartCount;
    }
    
    public void setAddToCartCount(Long addToCartCount) {
        this.addToCartCount = addToCartCount;
    }
    
    public Long getWishlistCount() {
        return wishlistCount;
    }
    
    public void setWishlistCount(Long wishlistCount) {
        this.wishlistCount = wishlistCount;
    }
    
    public Long getPurchaseCount() {
        return purchaseCount;
    }
    
    public void setPurchaseCount(Long purchaseCount) {
        this.purchaseCount = purchaseCount;
    }
    
    public BigDecimal getRevenue() {
        return revenue;
    }
    
    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }
    
    public Double getConversionRate() {
        return conversionRate;
    }
    
    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }
    
    public Integer getTrendingScore() {
        return trendingScore;
    }
    
    public void setTrendingScore(Integer trendingScore) {
        this.trendingScore = trendingScore;
    }
    
    public Trend getTrend() {
        return trend;
    }
    
    public void setTrend(Trend trend) {
        this.trend = trend;
    }
    
    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }
    
    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        calculatedAt = LocalDateTime.now();
        calculateConversionRate();
        calculateTrendingScore();
    }
    
    private void calculateConversionRate() {
        if (uniqueViewers > 0) {
            this.conversionRate = (purchaseCount.doubleValue() / uniqueViewers.doubleValue()) * 100;
        }
    }
    
    private void calculateTrendingScore() {
        // Weighted formula favoring recent engagement
        double score = (viewCount * 0.15) + 
                      (addToCartCount * 0.25) + 
                      (wishlistCount * 0.20) + 
                      (purchaseCount * 0.40);
        
        this.trendingScore = Math.min(100, (int) (score / 5));
    }
}
