package com.handmade.ecommerce.seller.dto.command;

import lombok.Data;

/**
 * Payload for approving reinstatement
 * Used in transition: PENDING_REINSTATEMENT → ACTIVE
 */
@Data
public class ApproveReinstatementPayload {
    private String approvedBy;
    private String approvalNotes;
    private Boolean requiresMonitoring;
    private Integer monitoringPeriodDays;
}
