package com.handmade.ecommerce.policy.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Payload for adding a rule to an onboarding policy.
 */
public class AddOnboardingPolicyRulePayload implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    public String stepName;
    public Integer stepOrder;
    public Boolean required;
    public String providerType;
    public Map<String, Object> providerConfig;
    public Integer maxRetries;
    public Integer retryDelayHours;
    public Integer maxDurationDays;
}
