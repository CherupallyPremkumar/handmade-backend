package com.homebase.admin.service;

import com.homebase.admin.mapper.OrderMapper;
import com.homebase.admin.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Report Service using MyBatis for complex queries
 * Demonstrates how to use MyBatis alongside JPA
 */
@Service
public class ReportService {

    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    public ReportService(OrderMapper orderMapper, ProductMapper productMapper) {
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
    }

    /**
     * Get comprehensive order statistics
     */
    public Map<String, Object> getOrderStatistics(String tenantId, String startDate, String endDate) {
        return orderMapper.getOrderStatistics(tenantId, startDate, endDate);
    }

    /**
     * Get top selling products
     */
    public List<Map<String, Object>> getTopSellingProducts(String tenantId, int limit) {
        return orderMapper.getTopSellingProducts(tenantId, limit);
    }

    /**
     * Get revenue breakdown by period (day, week, month)
     */
    public List<Map<String, Object>> getRevenueByPeriod(String tenantId, String period, 
                                                         String startDate, String endDate) {
        return orderMapper.getRevenueByPeriod(tenantId, period, startDate, endDate);
    }

    /**
     * Get top customers by total spent
     */
    public List<Map<String, Object>> getTopCustomers(String tenantId, int limit) {
        return orderMapper.getTopCustomers(tenantId, limit);
    }

    /**
     * Get products by category with statistics
     */
    public List<Map<String, Object>> getProductsByCategory(String tenantId) {
        return productMapper.getProductsByCategory(tenantId);
    }

    /**
     * Get pending orders count by status
     */
    public List<Map<String, Object>> getPendingOrdersCount(String tenantId) {
        return orderMapper.getPendingOrdersCount(tenantId);
    }
}
