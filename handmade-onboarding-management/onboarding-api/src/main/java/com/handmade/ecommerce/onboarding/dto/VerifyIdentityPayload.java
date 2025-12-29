package com.handmade.ecommerce.onboarding.dto;

import lombok.Data;

/**
 * Payload for verifying seller identity
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
