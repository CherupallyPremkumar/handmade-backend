package com.handmade.ecommerce.seller.onboarding.api.dto;

import lombok.Data;

@Data
public class CreateOnboardingRequest {
    private String email;
    private String businessName;
    private String businessType;
}
