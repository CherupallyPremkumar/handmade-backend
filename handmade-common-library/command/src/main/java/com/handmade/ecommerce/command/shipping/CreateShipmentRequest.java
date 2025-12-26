package com.handmade.ecommerce.command.shipping;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShipmentRequest {
    
    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    @NotBlank(message = "Carrier is required")
    private String carrier; // FEDEX, UPS, DHL, USPS
    
    @NotBlank(message = "Service type is required")
    private String serviceType; // STANDARD, EXPRESS, OVERNIGHT
    
    private String trackingNumber;
    private String shippingAddress;
}
