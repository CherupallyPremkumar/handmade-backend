package com.handmade.ecommerce.command.seller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerResponse {
    
    private Long id;
    private String businessName;
    private String contactEmail;
    private String contactPhone;
    private String businessType;
    private String status; // PENDING, ACTIVE, SUSPENDED, DEACTIVATED
    private String description;
    private String website;
    private Double rating;
    private Integer totalProducts;
    private Integer totalSales;
    private BigDecimal totalRevenue;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
