package com.handmade.ecommerce.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for Product response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    
    private String id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private String sellerId;
    private String sellerName;
    private String categoryId;
    private String categoryName;
    private String brand;
    private List<String> tags;
    private List<String> imageUrls;
    private Integer stockQuantity;
    private String status;
    private Double averageRating;
    private Integer reviewCount;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
