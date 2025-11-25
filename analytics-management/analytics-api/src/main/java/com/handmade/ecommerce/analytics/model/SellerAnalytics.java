package com.handmade.ecommerce.analytics.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * SellerAnalytics - Pre-aggregated seller metrics
 */
@Entity
@Table(name = "seller_analytics")
public class SellerAnalytics {
    
    @Id
    @Column(name = "analytics_id", length = 50)
    private String analyticsId;
    
    @Column(name = "seller_id", length = 50, nullable = false)
    private String sellerId;
    
    @Column(name = "total_orders")
    private Integer totalOrders;
    
    @Column(name = "pending_orders")
    private Integer pendingOrders;
    
    @Column(name = "completed_orders")
    private Integer completedOrders;
    
    @Column(name = "cancelled_orders")
    private Integer cancelledOrders;
    
    @Column(name = "total_revenue", precision = 19, scale = 2)
    private BigDecimal totalRevenue;
    
    @Column(name = "monthly_revenue", precision = 19, scale = 2)
    private BigDecimal monthlyRevenue;
    
    @Column(name = "average_order_value", precision = 19, scale = 2)
    private BigDecimal averageOrderValue;
    
    @Column(name = "total_products")
    private Integer totalProducts;
    
    @Column(name = "active_products")
    private Integer activeProducts;
    
    @Column(name = "out_of_stock_products")
    private Integer outOfStockProducts;
    
    @Column(name = "average_rating", precision = 3, scale = 2)
    private Double averageRating;
    
    @Column(name = "total_reviews")
    private Integer totalReviews;
    
    @Column(name = "total_customers")
    private Integer totalCustomers;
    
    @Column(name = "last_calculated_at")
    private Instant lastCalculatedAt;
    
    // Constructors
    public SellerAnalytics() {
        this.totalOrders = 0;
        this.totalRevenue = BigDecimal.ZERO;
        this.lastCalculatedAt = Instant.now();
    }
    
    // Getters and Setters
    public String getAnalyticsId() { return analyticsId; }
    public void setAnalyticsId(String analyticsId) { this.analyticsId = analyticsId; }
    
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    
    public Integer getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Integer totalOrders) { this.totalOrders = totalOrders; }
    
    public Integer getPendingOrders() { return pendingOrders; }
    public void setPendingOrders(Integer pendingOrders) { this.pendingOrders = pendingOrders; }
    
    public Integer getCompletedOrders() { return completedOrders; }
    public void setCompletedOrders(Integer completedOrders) { this.completedOrders = completedOrders; }
    
    public Integer getCancelledOrders() { return cancelledOrders; }
    public void setCancelledOrders(Integer cancelledOrders) { this.cancelledOrders = cancelledOrders; }
    
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    
    public BigDecimal getMonthlyRevenue() { return monthlyRevenue; }
    public void setMonthlyRevenue(BigDecimal monthlyRevenue) { this.monthlyRevenue = monthlyRevenue; }
    
    public BigDecimal getAverageOrderValue() { return averageOrderValue; }
    public void setAverageOrderValue(BigDecimal averageOrderValue) { this.averageOrderValue = averageOrderValue; }
    
    public Integer getTotalProducts() { return totalProducts; }
    public void setTotalProducts(Integer totalProducts) { this.totalProducts = totalProducts; }
    
    public Integer getActiveProducts() { return activeProducts; }
    public void setActiveProducts(Integer activeProducts) { this.activeProducts = activeProducts; }
    
    public Integer getOutOfStockProducts() { return outOfStockProducts; }
    public void setOutOfStockProducts(Integer outOfStockProducts) { this.outOfStockProducts = outOfStockProducts; }
    
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    
    public Integer getTotalReviews() { return totalReviews; }
    public void setTotalReviews(Integer totalReviews) { this.totalReviews = totalReviews; }
    
    public Integer getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(Integer totalCustomers) { this.totalCustomers = totalCustomers; }
    
    public Instant getLastCalculatedAt() { return lastCalculatedAt; }
    public void setLastCalculatedAt(Instant lastCalculatedAt) { this.lastCalculatedAt = lastCalculatedAt; }
}
