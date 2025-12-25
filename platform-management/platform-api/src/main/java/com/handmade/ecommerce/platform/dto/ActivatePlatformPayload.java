package com.handmade.ecommerce.platform.dto;

import lombok.Data;

/**
 * Payload for activating platform
 * Transition: BOOTSTRAPPING → ACTIVE
 */
@Data
public class ActivatePlatformPayload {
    private String activatedBy;
    private String notes;
}
