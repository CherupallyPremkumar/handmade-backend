package com.handmade.ecommerce.seller.dto.command;

import lombok.Data;

/**
 * Payload for deactivating seller account
 * Used in transition: SUSPENDED → DEACTIVATED
 */
@Data
public class DeactivateSellerPayload {
    private String deactivatedBy;
    private String reason;
    private String detailedExplanation;
    private Boolean allowReapplication;
    private Integer reapplicationCooldownDays;
}
