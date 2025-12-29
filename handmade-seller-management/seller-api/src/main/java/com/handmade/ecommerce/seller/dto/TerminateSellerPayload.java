package com.handmade.ecommerce.seller.dto.command;

import org.chenile.workflow.param.MinimalPayload;
import java.io.Serial;

public class TerminateSellerPayload extends MinimalPayload {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private String terminationComments;

    public String getTerminationComments() {
        return terminationComments;
    }

    public void setTerminationComments(String terminationComments) {
        this.terminationComments = terminationComments;
    }
}
