package com.handmade.ecommerce.seller.dto.command;

import lombok.Data;

/**
 * Payload for requesting reinstatement
 * Used in transition: SUSPENDED → PENDING_REINSTATEMENT
 */
@Data
public class RequestReinstatementPayload {
    private String appealText;
    private String planOfAction; // Seller's plan to prevent future issues
    private String rootCauseAnalysis;
    private String correctiveActions;
    private String preventiveMeasures;
}
