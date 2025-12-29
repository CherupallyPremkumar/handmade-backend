package com.handmade.ecommerce.platform.dto;

import lombok.Data;

/**
 * Payload for exiting maintenance mode
 */
@Data
public class ExitMaintenancePayload {
    private String completionNotes;
    private String completedBy;
}
