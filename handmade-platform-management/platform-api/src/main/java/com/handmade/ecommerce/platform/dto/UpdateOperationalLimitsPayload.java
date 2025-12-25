package com.handmade.ecommerce.platform.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * Payload for updating operational limits
 */
@Data
public class UpdateOperationalLimitsPayload {
    private Integer maxSellersPerDay;
    private Integer maxProductsPerSeller;
    private BigDecimal maxTransactionAmount;
    private Integer globalRateLimit;
    private Integer maxOrdersPerHour;
    private String updatedBy;
    private String reason;
}
