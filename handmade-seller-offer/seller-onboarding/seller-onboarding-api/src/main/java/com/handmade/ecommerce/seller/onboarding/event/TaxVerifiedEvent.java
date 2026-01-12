package com.handmade.ecommerce.seller.onboarding.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxVerifiedEvent {
    private String caseId;
    private String sellerId;
    private Map<String, Object> verificationData;
}
