package com.handmade.ecommerce.seller.dto.command;

import lombok.Data;

/**
 * Payload for flagging performance issues
 * Used in transition: ACTIVE → AT_RISK
 */
@Data
public class FlagPerformanceIssuePayload {
    private String issueType; // HIGH_ODR, HIGH_LSR, LOW_TRACKING_RATE, SLOW_RESPONSE_TIME
    private String description;
    private Double currentMetricValue;
    private Double thresholdValue;
    private String detectedBy; // SYSTEM or admin user
    private Boolean autoFlagged;
}
