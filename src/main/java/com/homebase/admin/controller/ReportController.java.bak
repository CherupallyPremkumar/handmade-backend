package com.homebase.admin.controller;

import com.homebase.admin.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Report Controller
 * Provides analytics and reporting endpoints using MyBatis queries
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * GET /api/reports/order-statistics
     * Get order statistics for a date range
     */
    @GetMapping("/order-statistics")
    public ResponseEntity<Map<String, Object>> getOrderStatistics(
            @RequestParam(required = false, defaultValue = "default") String tenantId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        Map<String, Object> stats = reportService.getOrderStatistics(tenantId, startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    /**
     * GET /api/reports/top-products
     * Get top selling products
     */
    @GetMapping("/top-products")
    public ResponseEntity<List<Map<String, Object>>> getTopProducts(
            @RequestParam(required = false, defaultValue = "default") String tenantId,
            @RequestParam(required = false, defaultValue = "10") int limit) {
        List<Map<String, Object>> products = reportService.getTopSellingProducts(tenantId, limit);
        return ResponseEntity.ok(products);
    }

    /**
     * GET /api/reports/revenue
     * Get revenue breakdown by period
     */
    @GetMapping("/revenue")
    public ResponseEntity<List<Map<String, Object>>> getRevenue(
            @RequestParam(required = false, defaultValue = "default") String tenantId,
            @RequestParam(required = false, defaultValue = "day") String period,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        List<Map<String, Object>> revenue = reportService.getRevenueByPeriod(
                tenantId, period, startDate, endDate);
        return ResponseEntity.ok(revenue);
    }

    /**
     * GET /api/reports/top-customers
     * Get top customers by spending
     */
    @GetMapping("/top-customers")
    public ResponseEntity<List<Map<String, Object>>> getTopCustomers(
            @RequestParam(required = false, defaultValue = "default") String tenantId,
            @RequestParam(required = false, defaultValue = "10") int limit) {
        List<Map<String, Object>> customers = reportService.getTopCustomers(tenantId, limit);
        return ResponseEntity.ok(customers);
    }

    /**
     * GET /api/reports/products-by-category
     * Get product statistics by category
     */
    @GetMapping("/products-by-category")
    public ResponseEntity<List<Map<String, Object>>> getProductsByCategory(
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        List<Map<String, Object>> categoryStats = reportService.getProductsByCategory(tenantId);
        return ResponseEntity.ok(categoryStats);
    }

    /**
     * GET /api/reports/pending-orders
     * Get pending orders count by status
     */
    @GetMapping("/pending-orders")
    public ResponseEntity<List<Map<String, Object>>> getPendingOrders(
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        List<Map<String, Object>> pendingOrders = reportService.getPendingOrdersCount(tenantId);
        return ResponseEntity.ok(pendingOrders);
    }
}
