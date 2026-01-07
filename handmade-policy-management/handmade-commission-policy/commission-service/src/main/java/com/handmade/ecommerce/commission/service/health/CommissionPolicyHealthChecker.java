package com.handmade.ecommerce.commission.service.health;


import org.chenile.core.service.HealthCheckInfo;
import org.chenile.core.service.HealthChecker;
import org.springframework.stereotype.Component;

@Component
public class CommissionPolicyHealthChecker implements HealthChecker {


    @Override
    public HealthCheckInfo healthCheck() {
        return new HealthCheckInfo();
    }
}
