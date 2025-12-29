package com.handmade.ecommerce.onboarding.dto;

import org.chenile.workflow.param.MinimalPayload;
import java.io.Serial;

public class RejectSellerPayload extends MinimalPayload {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private String rejectionReason;

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
