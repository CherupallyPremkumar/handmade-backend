package com.homebase.admin.dto;


import java.math.BigDecimal;


public class DashboardStatsDTO {
    private Long totalProducts;
    private Long totalOrders;
    private BigDecimal totalSales;
    private Long lowStockProducts;

    public DashboardStatsDTO(Long totalProducts, Long totalOrders, BigDecimal totalSales, Long lowStockProducts) {
        this.totalProducts = totalProducts;
        this.totalOrders = totalOrders;
        this.totalSales = totalSales;
        this.lowStockProducts = lowStockProducts;
    }

    public Long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public Long getLowStockProducts() {
        return lowStockProducts;
    }

    public void setLowStockProducts(Long lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }
}
