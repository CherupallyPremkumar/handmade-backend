package com.handmade.ecommerce.seller.onboarding.api.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnboardingApprovedEvent implements Serializable {
    private String caseId;
    private String sellerId;
    private String email;
    private String businessName;
    private String businessType;
    private Map<String, Object> verifiedData;
}
