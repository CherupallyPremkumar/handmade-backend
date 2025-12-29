package com.handmade.ecommerce.stripe.api;

import lombok.Builder;
import lombok.Data;

/**
 * Tax registration result from Stripe
 * Returned when seller completes tax interview
 */
@Data
@Builder
public class StripeTaxRegistrationResult {
    private String registrationId;      // Stripe tax registration ID
    private String formType;             // W9, W8_BEN, W8_BEN_E
    private boolean isUsPerson;
    private boolean completed;
    private String status;               // PENDING, COMPLETED, FAILED
}
