package com.handmade.ecommerce.platform.dto;

import lombok.Data;

/**
 * Payload for deleting platform
 * Transition: Any state → DELETED
 */
public class DeletePlatformPayload {
    public String deletionReason;
    public String deletedBy;
    public String notes;
}
