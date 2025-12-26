package com.handmade.ecommerce.command.promotion;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
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
public class CreatePromotionRequest {
    
    @NotBlank(message = "Promotion name is required")
    @Size(max = 200)
    private String name;
    
    @NotBlank(message = "Promotion code is required")
    @Size(max = 50)
    private String code;
    
    @NotBlank(message = "Discount type is required")
    private String discountType; // PERCENTAGE, FIXED_AMOUNT
    
    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.0")
    private BigDecimal discountValue;
    
    @NotNull(message = "Start date is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    
    private BigDecimal minOrderValue;
    private BigDecimal maxDiscountAmount;
    private Integer usageLimit;
    private Boolean isActive;
}
