package com.handmade.ecommerce.seller.onboarding.api.dto;

import org.chenile.workflow.param.MinimalPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RejectOnboardingPayload extends MinimalPayload {
    private String reason;
}
