package com.handmade.ecommerce.platform.core.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * Fixed Clock Implementation (Testing)
 * 
 * Returns a fixed point in time, useful for deterministic testing.
 * 
 * Example:
 * <pre>
 * PlatformClock clock = FixedClock.at(Instant.parse("2025-01-15T10:00:00Z"));
 * assertThat(clock.today()).isEqualTo(LocalDate.of(2025, 1, 15));
 * </pre>
 */
public class FixedClock implements PlatformClock {
    
    private final Instant fixedInstant;
    
    private FixedClock(Instant fixedInstant) {
        this.fixedInstant = fixedInstant;
    }
    
    /**
     * Creates a fixed clock at the specified instant.
     */
    public static FixedClock at(Instant instant) {
        return new FixedClock(instant);
    }
    
    /**
     * Creates a fixed clock at the specified date (midnight UTC).
     */
    public static FixedClock at(LocalDate date) {
        return new FixedClock(date.atStartOfDay(ZoneOffset.UTC).toInstant());
    }
    
    @Override
    public Instant instant() {
        return fixedInstant;
    }
    
    @Override
    public LocalDate today() {
        return fixedInstant.atZone(ZoneOffset.UTC).toLocalDate();
    }
    
    @Override
    public String toString() {
        return "FixedClock[" + fixedInstant + "]";
    }
}
