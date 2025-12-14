package com.handmade.ecommerce.command.seller;

import jakarta.validation.constraints.Email;
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
public class CreateSellerRequest {
    
    @NotBlank(message = "Business name is required")
    @Size(max = 200)
    private String businessName;
    
    @NotBlank(message = "Contact email is required")
    @Email
    private String contactEmail;
    
    @Size(max = 20)
    private String contactPhone;
    
    @NotBlank(message = "Business type is required")
    private String businessType; // INDIVIDUAL, COMPANY, PARTNERSHIP
    
    private String taxId;
    private String businessRegistrationNumber;
    private String description;
    private String website;
}
