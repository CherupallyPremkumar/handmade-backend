package com.handmade.ecommerce.seller.dto.command;

import lombok.Data;

/**
 * Payload for verifying seller identity
 * Used in transition: IDENTITY_VERIFICATION → TAX_VERIFICATION
 */
@Data
public class VerifyIdentityPayload {
    private String verifiedBy;
    private String identityDocumentType; // PASSPORT, DRIVERS_LICENSE, NATIONAL_ID
    private String identityDocumentNumber;
    private Boolean selfieVerified;
    private Boolean addressProofVerified;
    private String verificationNotes;
}
