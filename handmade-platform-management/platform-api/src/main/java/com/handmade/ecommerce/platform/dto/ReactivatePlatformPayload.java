package com.handmade.ecommerce.platform.dto;

import lombok.Data;

/**
 * Payload for reactivating platform
 * Transition: SUSPENDED → ACTIVE
 */
@Data
public class ReactivatePlatformPayload {
    private String reactivatedBy;
    private String notes;
}
