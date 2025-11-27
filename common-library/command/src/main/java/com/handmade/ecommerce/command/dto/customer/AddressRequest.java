package com.handmade.ecommerce.command.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    
    @NotBlank(message = "Street address is required")
    @Size(max = 200)
    private String street;
    
    @NotBlank(message = "City is required")
    @Size(max = 100)
    private String city;
    
    @NotBlank(message = "State/Province is required")
    @Size(max = 100)
    private String state;
    
    @NotBlank(message = "Postal code is required")
    @Size(max = 20)
    private String postalCode;
    
    @NotBlank(message = "Country is required")
    @Size(max = 100)
    private String country;
    
    private String addressType; // SHIPPING, BILLING, BOTH
    private Boolean isDefault;
}
