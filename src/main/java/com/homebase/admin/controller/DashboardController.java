package com.homebase.admin.controller;

import com.homebase.admin.dto.DashboardStatsDTO;
import com.homebase.admin.dto.OrderDTO;
import com.homebase.admin.dto.ProductDTO;
import com.homebase.admin.service.DashboardService;
import com.homebase.admin.service.OrderService;
import com.homebase.admin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getStats() {
        DashboardStatsDTO stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/recent-orders")
    public ResponseEntity<List<OrderDTO>> getRecentOrders() {
        List<OrderDTO> orders = orderService.getRecentOrders(5);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductDTO>> getLowStockProducts() {
        List<ProductDTO> products = productService.getLowStockProducts(10);
        return ResponseEntity.ok(products);
    }
}
