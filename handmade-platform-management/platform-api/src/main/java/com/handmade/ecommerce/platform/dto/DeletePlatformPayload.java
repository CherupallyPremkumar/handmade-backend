package com.handmade.ecommerce.platform.dto;

import lombok.Data;

/**
 * Payload for deleting platform
 * Transition: Any state → DELETED
 */
@Data
public class DeletePlatformPayload {
    private String reason;
    private String deletedBy;
    private String notes;
}
