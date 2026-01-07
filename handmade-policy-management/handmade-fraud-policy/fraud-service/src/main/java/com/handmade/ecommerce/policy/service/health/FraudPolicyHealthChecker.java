package com.handmade.ecommerce.policy.service.health;

import org.chenile.core.service.HealthCheckInfo;
import org.chenile.core.service.HealthChecker;

public class FraudPolicyHealthChecker implements HealthChecker {
    @Override
    public HealthCheckInfo healthCheck() {
        HealthCheckInfo info = new HealthCheckInfo();
        info.healthy = true;
        info.message = "Fraud Policy Service is healthy";
        return info;
    }
}
