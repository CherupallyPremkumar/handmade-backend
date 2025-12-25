package com.handmade.ecommerce.platform.dto;

import lombok.Data;

/**
 * Payload for entering maintenance mode
 */
@Data
public class EnterMaintenancePayload {
    private String reason;
    private String estimatedDuration;
    private String maintenanceMessage;
    private String scheduledBy;
}
