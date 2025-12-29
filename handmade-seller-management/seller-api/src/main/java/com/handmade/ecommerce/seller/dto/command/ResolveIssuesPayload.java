package com.handmade.ecommerce.seller.dto.command;

import lombok.Data;

/**
 * Payload for resolving performance issues
 * Used in transition: AT_RISK → ACTIVE
 */
@Data
public class ResolveIssuesPayload {
    private String resolvedBy;
    private String resolutionNotes;
    private Boolean metricsImproved;
    private String improvementDetails;
}
