package com.handmade.ecommerce.command.analytics;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesAnalyticsResponse {
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    private Integer totalOrders;
    private BigDecimal totalRevenue;
    private BigDecimal averageOrderValue;
    private Integer newCustomers;
    private Integer returningCustomers;
    private BigDecimal conversionRate;
}
