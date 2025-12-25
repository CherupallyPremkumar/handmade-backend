package com.handmade.ecommerce.platform.dto;

import lombok.Data;

/**
 * Payload for publishing legal terms and conditions
 */
@Data
public class PublishLegalTermsPayload {
    private String termsOfServiceUrl;
    private String privacyPolicyUrl;
    private String refundPolicyUrl;
    private String cookiePolicyUrl;
    private String effectiveDate;
    private String version;
}
