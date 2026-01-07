package com.handmade.ecommerce.policy.service.health;

import org.chenile.core.service.HealthCheckInfo;
import org.chenile.core.service.HealthChecker;

/**
 * Health checker for pricing policy service.
 */
public class PricingPolicyHealthChecker implements HealthChecker {

    @Override
    public HealthCheckInfo healthCheck() {
        HealthCheckInfo info = new HealthCheckInfo();
        info.healthy = true;
        info.message = "Pricing Policy Service is healthy";
        return info;
    }
}
