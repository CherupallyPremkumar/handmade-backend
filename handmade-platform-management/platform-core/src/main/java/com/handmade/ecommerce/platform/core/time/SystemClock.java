package com.handmade.ecommerce.platform.core.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * System Clock Implementation (Production)
 * 
 * Default implementation that delegates to system time.
 * Always returns UTC-based time.
 * 
 * Thread-safe and stateless.
 * 
 * Usage: Inject via Spring, do NOT use static access.
 */
public class SystemClock implements PlatformClock {
    
    /**
     * Public constructor for Spring instantiation.
     * Do NOT create instances manually - use dependency injection.
     */
    public SystemClock() {
        // Default constructor for Spring
    }
    
    @Override
    public Instant instant() {
        return Instant.now();
    }
    
    @Override
    public LocalDate today() {
        return LocalDate.now(ZoneOffset.UTC);
    }
    
    @Override
    public String toString() {
        return "SystemClock[UTC]";
    }
}
