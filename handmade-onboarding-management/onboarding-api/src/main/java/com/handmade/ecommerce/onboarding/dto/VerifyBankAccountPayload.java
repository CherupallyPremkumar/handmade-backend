package com.handmade.ecommerce.onboarding.dto;

import org.chenile.workflow.param.MinimalPayload;
import java.io.Serial;

public class VerifyBankAccountPayload extends MinimalPayload {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private boolean verified;
    private String payoutToken;
    private String provider;
    private String comments;

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getPayoutToken() {
        return payoutToken;
    }

    public void setPayoutToken(String payoutToken) {
        this.payoutToken = payoutToken;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
