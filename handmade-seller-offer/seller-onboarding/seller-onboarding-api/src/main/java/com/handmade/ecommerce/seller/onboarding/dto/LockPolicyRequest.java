package com.handmade.ecommerce.seller.onboarding.dto;

import lombok.Data;
import org.chenile.workflow.param.MinimalPayload;

@Data
public class LockPolicyRequest  extends MinimalPayload {
    private String policyId;
}
