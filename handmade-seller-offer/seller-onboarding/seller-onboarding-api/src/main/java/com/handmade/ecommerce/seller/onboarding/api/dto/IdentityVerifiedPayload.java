package com.handmade.ecommerce.seller.onboarding.api.dto;

import org.chenile.workflow.param.MinimalPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class IdentityVerifiedPayload extends MinimalPayload {
    private Map<String, Object> verificationData;
}
