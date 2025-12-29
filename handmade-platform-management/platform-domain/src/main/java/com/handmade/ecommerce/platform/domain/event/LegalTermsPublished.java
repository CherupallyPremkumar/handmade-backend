package com.handmade.ecommerce.platform.domain.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Event emitted when new legal terms (ToS, Privacy) are published.
 */
public record LegalTermsPublished(
    UUID platformId,
    UUID documentId,
    String documentType,
    String version,
    Instant effectiveDate,
    Instant occurredAt
) {}
