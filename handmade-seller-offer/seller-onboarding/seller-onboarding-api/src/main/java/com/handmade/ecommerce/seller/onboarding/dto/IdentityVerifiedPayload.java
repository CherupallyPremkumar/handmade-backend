package com.handmade.ecommerce.seller.onboarding.dto;

import org.chenile.workflow.param.MinimalPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
public class IdentityVerifiedPayload  extends MinimalPayload{
    private Map<String, Object> verificationData;
}
