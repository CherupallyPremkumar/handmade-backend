package com.handmade.ecommerce.policy.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Payload for updating a rule in an onboarding policy.
 */
public class UpdateOnboardingPolicyRulePayload implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    public String stepName; // Identifier for the rule within policy
    public Integer stepOrder;
    public Boolean required;
    public String providerType;
    public Map<String, Object> providerConfig;
    public Integer maxRetries;
    public Integer retryDelayHours;
    public Integer maxDurationDays;
}
