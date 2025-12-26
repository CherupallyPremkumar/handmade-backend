package com.handmade.ecommerce.command.dispute;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDisputeRequest {
    
    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotBlank(message = "Reason is required")
    private String reason; // PRODUCT_NOT_RECEIVED, DAMAGED_PRODUCT, WRONG_ITEM, QUALITY_ISSUE, OTHER
    
    @NotBlank(message = "Description is required")
    @Size(max = 2000)
    private String description;
    
    private String evidence;
}
