package com.handmade.ecommerce.onboarding.dto;

import org.chenile.workflow.param.MinimalPayload;
import java.io.Serial;

public class SubmitAdditionalInfoPayload extends MinimalPayload {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private String additionalInfo;

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
