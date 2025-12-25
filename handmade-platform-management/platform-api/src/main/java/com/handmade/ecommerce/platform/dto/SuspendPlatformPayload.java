package com.handmade.ecommerce.platform.dto;

import lombok.Data;

/**
 * Payload for suspending platform
 * Transition: ACTIVE → SUSPENDED
 */
public class SuspendPlatformPayload {
    public String suspensionReason;
    public String suspendedBy;
    public String notes;
}
