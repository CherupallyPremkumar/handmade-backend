package com.handmade.ecommerce.analytics.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * CategoryAnalytics - Aggregate Root
 * Tracks performance metrics for categories
 */
@Entity
@Table(name = "category_analytics", indexes = {
    @Index(name = "idx_category_date", columnList = "category_id, analytics_date"),
    @Index(name = "idx_popularity_score", columnList = "popularity_score")
})
public class CategoryAnalytics {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analytics_id")
    private Long analyticsId;
    
    @Column(name = "category_id", nullable = false, length = 50)
    private String categoryId;
    
    @Column(name = "analytics_date", nullable = false)
    private LocalDate analyticsDate;
    
    @Column(name = "view_count")
    private Long viewCount = 0L;
    
    @Column(name = "unique_visitors")
    private Long uniqueVisitors = 0L;
    
    @Column(name = "product_clicks")
    private Long productClicks = 0L;
    
    @Column(name = "add_to_cart_count")
    private Long addToCartCount = 0L;
    
    @Column(name = "purchase_count")
    private Long purchaseCount = 0L;
    
    @Column(name = "conversion_rate")
    private Double conversionRate = 0.0; // Percentage
    
    @Column(name = "popularity_score")
    private Integer popularityScore = 0; // 0-100
    
    @Enumerated(EnumType.STRING)
    @Column(name = "trend", length = 10)
    private Trend trend; // UP, DOWN, STABLE
    
    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;
    
    // Getters and Setters
    
    public Long getAnalyticsId() {
        return analyticsId;
    }
    
    public void setAnalyticsId(Long analyticsId) {
        this.analyticsId = analyticsId;
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
    
    public Long getUniqueVisitors() {
        return uniqueVisitors;
    }
    
    public void setUniqueVisitors(Long uniqueVisitors) {
        this.uniqueVisitors = uniqueVisitors;
    }
    
    public Long getProductClicks() {
        return productClicks;
    }
    
    public void setProductClicks(Long productClicks) {
        this.productClicks = productClicks;
    }
    
    public Long getAddToCartCount() {
        return addToCartCount;
    }
    
    public void setAddToCartCount(Long addToCartCount) {
        this.addToCartCount = addToCartCount;
    }
    
    public Long getPurchaseCount() {
        return purchaseCount;
    }
    
    public void setPurchaseCount(Long purchaseCount) {
        this.purchaseCount = purchaseCount;
    }
    
    public Double getConversionRate() {
        return conversionRate;
    }
    
    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }
    
    public Integer getPopularityScore() {
        return popularityScore;
    }
    
    public void setPopularityScore(Integer popularityScore) {
        this.popularityScore = popularityScore;
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
        calculatePopularityScore();
    }
    
    /**
     * Calculate conversion rate
     */
    private void calculateConversionRate() {
        if (uniqueVisitors > 0) {
            this.conversionRate = (purchaseCount.doubleValue() / uniqueVisitors.doubleValue()) * 100;
        }
    }
    
    /**
     * Calculate popularity score (0-100)
     */
    private void calculatePopularityScore() {
        // Simple weighted formula
        double score = (viewCount * 0.1) + 
                      (productClicks * 0.2) + 
                      (addToCartCount * 0.3) + 
                      (purchaseCount * 0.4);
        
        // Normalize to 0-100
        this.popularityScore = Math.min(100, (int) (score / 10));
    }
}
