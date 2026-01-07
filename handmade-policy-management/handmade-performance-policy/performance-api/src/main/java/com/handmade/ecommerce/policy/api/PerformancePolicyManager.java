package com.handmade.ecommerce.policy.api;

import com.handmade.ecommerce.policy.domain.aggregate.PerformancePolicy;
import com.handmade.ecommerce.policy.domain.valueobject.PerformanceDecision;
import org.chenile.workflow.api.StateEntityService;

public interface PerformancePolicyManager extends StateEntityService<PerformancePolicy> {
    PerformanceDecision evaluateSellerPerformance(String sellerId);
}
