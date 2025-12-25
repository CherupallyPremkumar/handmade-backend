package com.handmade.ecommerce.platform.domain.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Event emitted when the platform commission structure is revised.
 * Immutable.
 */
public record CommissionStructureRevised(
    UUID platformId,
    UUID newPolicyId,
    Instant effectiveDate,
    String reason,
    Instant occurredAt
) {
    public CommissionStructureRevised {
        if (platformId == null) throw new IllegalArgumentException("Platform ID required");
        if (newPolicyId == null) throw new IllegalArgumentException("Policy ID required");
    }
}
