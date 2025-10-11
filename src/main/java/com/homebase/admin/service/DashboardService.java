package com.homebase.admin.service;

import com.homebase.admin.dto.DashboardStatsDTO;
import com.homebase.admin.config.TenantContext;
import com.homebase.admin.repository.OrderRepository;
import com.homebase.admin.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public DashboardService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public DashboardStatsDTO getDashboardStats() {
        String tenantId = TenantContext.getCurrentTenant();

        Long totalProducts = productRepository.countByTenantId(tenantId);
        Long totalOrders = orderRepository.countByTenantId(tenantId);
        BigDecimal totalSales = orderRepository.getTotalSalesByTenant(tenantId);
        Long lowStockProducts = (long) productRepository.findLowStockProducts(tenantId, 10).size();

        return new DashboardStatsDTO(
                totalProducts,
                totalOrders,
                totalSales != null ? totalSales : BigDecimal.ZERO,
                lowStockProducts);
    }
}
