package com.handmade.ecommerce.platform.dto;

import lombok.Data;

/**
 * Payload for suspending platform
 * Transition: ACTIVE → SUSPENDED
 */
@Data
public class SuspendPlatformPayload {
    private String reason;
    private String suspendedBy;
    private String notes;
}
