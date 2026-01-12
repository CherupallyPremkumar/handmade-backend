package com.handmade.ecommerce.seller.onboarding.dto;

import lombok.Data;
import org.chenile.workflow.param.MinimalPayload;

@Data
public class CreateOnboardingRequest extends MinimalPayload {
    private String email;
    private String businessName;
    private String businessType;
}
