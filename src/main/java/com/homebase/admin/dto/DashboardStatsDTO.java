package com.homebase.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private Long totalProducts;
    private Long totalOrders;
    private BigDecimal totalSales;
    private Long lowStockProducts;
}
