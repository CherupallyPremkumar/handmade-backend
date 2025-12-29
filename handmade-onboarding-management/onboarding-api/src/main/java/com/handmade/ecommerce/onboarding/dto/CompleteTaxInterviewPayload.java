package com.handmade.ecommerce.onboarding.dto;

import lombok.Data;

/**
 * Payload for completing tax interview
 */
@Data
public class CompleteTaxInterviewPayload {
    private String taxFormType; // W9, W8_BEN, W8_BEN_E
    private String taxId; // SSN or EIN
    private String taxClassification; // INDIVIDUAL, C_CORP, S_CORP, PARTNERSHIP, LLC
    private Boolean foreignTaxpayer;
    private String countryOfResidence;
    private String completedBy;
}
