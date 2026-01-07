package com.handmade.ecommerce.policy.service.health;

import org.chenile.core.service.HealthCheckInfo;
import org.chenile.core.service.HealthChecker;

public class ReturnPolicyHealthChecker implements HealthChecker {
    @Override
    public HealthCheckInfo healthCheck() {
        HealthCheckInfo info = new HealthCheckInfo();
        info.healthy = true;
        info.message = "Return Policy Service is healthy";
        return info;
    }
}
