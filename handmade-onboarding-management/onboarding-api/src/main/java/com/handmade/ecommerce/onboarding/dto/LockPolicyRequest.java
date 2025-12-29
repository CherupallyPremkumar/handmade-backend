package com.handmade.ecommerce.onboarding.dto;

import org.chenile.workflow.param.MinimalPayload;

import java.io.Serial;

/**
 * Payload for locking policy version to an onboarding case.
 * Triggers DRAFT → POLICY_LOCKED transition.
 */
public class LockPolicyRequest extends MinimalPayload {
    @Serial
    private static final long serialVersionUID = 1L;

    private String policyId;
    private String policyVersion;

    public LockPolicyRequest() {
    }

    public LockPolicyRequest(String policyId, String policyVersion) {
        this.policyId = policyId;
        this.policyVersion = policyVersion;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getPolicyVersion() {
        return policyVersion;
    }

    public void setPolicyVersion(String policyVersion) {
        this.policyVersion = policyVersion;
    }
}
