package com.handmade.ecommerce.policy.dto;

import com.handmade.ecommerce.platform.domain.enums.SellerType;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Payload for updating an onboarding policy draft.
 */
public class UpdateOnboardingPolicyPayload implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    public String policyVersion;
    public String countryCode;
    public SellerType sellerType;
    public LocalDate effectiveDate;
    public String description;
    public String regulatoryBasis;
}
