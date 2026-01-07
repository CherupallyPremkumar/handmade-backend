package com.handmade.ecommerce.policy.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 * Payload for removing a rule from an onboarding policy.
 */
public class RemoveOnboardingPolicyRulePayload implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    public String stepName;
}
