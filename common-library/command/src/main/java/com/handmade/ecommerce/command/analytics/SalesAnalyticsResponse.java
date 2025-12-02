package com.handmade.ecommerce.command.analytics;



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
