package com.handmade.ecommerce.identity.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Value object representing an external verification session details.
 * Used for inter-layer communication within the domain.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalVerificationSession {
    private String sessionId;
    private String clientSecret;
    private String url;
}
