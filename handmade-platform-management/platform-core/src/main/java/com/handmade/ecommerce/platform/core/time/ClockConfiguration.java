package com.handmade.ecommerce.platform.core.time;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Configuration for Platform Clock
 * 
 * Provides a singleton PlatformClock bean for dependency injection.
 * Uses SystemClock by default (production).
 * 
 * Override this bean in test configurations to inject FixedClock.
 */
@Configuration
public class ClockConfiguration {
    
    @Bean
    public PlatformClock platformClock() {
        return new SystemClock();
    }
}
