package com.handmade.ecommerce.policy.service.health;

import org.chenile.core.service.HealthCheckInfo;
import org.chenile.core.service.HealthChecker;

/**
 * Health checker for onboarding policy service.
 */
public class OnboardingPolicyHealthChecker implements HealthChecker {

    @Override
    public HealthCheckInfo healthCheck() {
        HealthCheckInfo info = new HealthCheckInfo();
        info.healthy = true;
        info.message = "Onboarding Policy Service is healthy";
        return info;
    }
}
