package com.handmade.ecommerce.onboarding.dto;

import org.chenile.workflow.param.MinimalPayload;
import java.io.Serial;

public class VerifyKYCPayload extends MinimalPayload {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private boolean verified;
    private String comments;

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
