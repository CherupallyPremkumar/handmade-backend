package com.handmade.ecommerce.platform.dto;

import java.io.Serializable;

public class ReviseCommissionPayload implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String newCommissionPolicyId;
    public String reason;
}
