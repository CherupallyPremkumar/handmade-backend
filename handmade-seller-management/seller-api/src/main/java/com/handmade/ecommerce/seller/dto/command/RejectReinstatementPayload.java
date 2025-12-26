package com.handmade.ecommerce.seller.dto.command;

import lombok.Data;

/**
 * Payload for rejecting reinstatement
 * Used in transition: PENDING_REINSTATEMENT → DEACTIVATED
 */
@Data
public class RejectReinstatementPayload {
    private String rejectedBy;
    private String rejectionReason;
    private String detailedExplanation;
    private Boolean permanentBan;
}
