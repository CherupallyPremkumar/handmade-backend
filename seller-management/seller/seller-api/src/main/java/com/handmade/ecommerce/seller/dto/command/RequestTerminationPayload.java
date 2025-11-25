package com.handmade.ecommerce.seller.dto.command;

import org.chenile.workflow.param.MinimalPayload;
import java.io.Serial;

public class RequestTerminationPayload extends MinimalPayload {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private String terminationReason;

    public String getTerminationReason() {
        return terminationReason;
    }

    public void setTerminationReason(String terminationReason) {
        this.terminationReason = terminationReason;
    }
}
