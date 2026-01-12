package com.handmade.ecommerce.seller.onboarding.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingResponse {
    private String id;
    private String sellerId;
    private String email;
    private String businessName;
    private String businessType;
    private String onboardingStatus;
    private String state;
    private BigDecimal completionPercentage;
    private Date startedAt;
    private Date completedAt;
    private List<OnboardingStepResponse> steps;
}
