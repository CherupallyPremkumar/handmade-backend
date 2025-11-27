package com.handmade.ecommerce.command.dto.commission;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommissionRuleRequest {
    
    @NotBlank(message = "Rule name is required")
    private String ruleName;
    
    @NotBlank(message = "Rule type is required")
    private String ruleType; // PERCENTAGE, FIXED, TIERED
    
    @NotNull(message = "Commission rate is required")
    @DecimalMin(value = "0.0", message = "Commission rate cannot be negative")
    private BigDecimal commissionRate;
    
    private Long categoryId;
    private Long sellerId;
    private BigDecimal minOrderValue;
    private BigDecimal maxOrderValue;
    private String description;
}
