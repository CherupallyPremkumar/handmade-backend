package com.handmade.ecommerce.policy.service.health;

import org.chenile.core.service.HealthCheckInfo;
import org.chenile.core.service.HealthChecker;

public class PerformancePolicyHealthChecker implements HealthChecker {
    @Override
    public HealthCheckInfo healthCheck() {
        HealthCheckInfo info = new HealthCheckInfo();
        info.healthy = true;
        info.message = "Performance Policy Service is healthy";
        return info;
    }
}
