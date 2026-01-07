package com.handmade.ecommerce.seller.account.dto;

import org.chenile.workflow.param.MinimalPayload;
import java.io.Serial;

public class RequestMoreInfoPayload extends MinimalPayload {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private String infoRequested;

    public String getInfoRequested() {
        return infoRequested;
    }

    public void setInfoRequested(String infoRequested) {
        this.infoRequested = infoRequested;
    }
}
