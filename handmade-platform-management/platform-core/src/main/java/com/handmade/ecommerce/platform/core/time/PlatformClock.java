package com.handmade.ecommerce.platform.core.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * Platform Clock Abstraction
 * 
 * Provides a centralized, testable source of time for all bounded contexts.
 * 
 * Design Rationale:
 * - Eliminates direct calls to Instant.now() or LocalDate.now() in business logic
 * - Enables deterministic testing by allowing time injection
 * - Ensures consistent UTC-based time across all modules
 * - Prevents timezone-related bugs in distributed systems
 * 
 * Usage:
 * - Inject this interface into services, not the implementation
 * - Use SystemClock for production
 * - Use FixedClock or OffsetClock for testing
 * 
 * Amazon-Style Principle:
 * "Time is infrastructure, not business logic. Abstract it."
 * 
 * Note: Named PlatformClock (not Clock) to avoid confusion with java.time.Clock
 */
public interface PlatformClock {
    
    /**
     * Returns the current instant in UTC.
     * 
     * @return current UTC instant
     */
    Instant instant();
    
    /**
     * Returns the current date in UTC timezone.
     * 
     * @return current UTC date
     */
    LocalDate today();
    
    /**
     * Returns the epoch milliseconds (UTC).
     * 
     * @return milliseconds since epoch
     */
    default long millis() {
        return instant().toEpochMilli();
    }
}
