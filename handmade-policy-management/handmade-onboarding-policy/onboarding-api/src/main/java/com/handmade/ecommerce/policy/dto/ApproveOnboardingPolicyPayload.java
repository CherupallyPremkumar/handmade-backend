package com.handmade.ecommerce.policy.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 * Payload for approving an onboarding policy.
 */
public class ApproveOnboardingPolicyPayload implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    public String approvedBy;
    public String remarks;
}
