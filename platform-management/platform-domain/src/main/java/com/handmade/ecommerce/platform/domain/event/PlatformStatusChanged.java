package com.handmade.ecommerce.platform.domain.event;

import com.handmade.ecommerce.platform.domain.valueobject.PlatformLifecycle;
import java.time.Instant;
import java.util.UUID;

/**
 * Event emitted when the platform lifecycle state changes.
 */
public record PlatformStatusChanged(
    UUID platformId,
    PlatformLifecycle oldState,
    PlatformLifecycle newState,
    String reason,
    String operatorId,
    Instant occurredAt
) {}
