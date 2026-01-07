package com.handmade.ecommerce.policy.api;

import com.handmade.ecommerce.policy.domain.aggregate.FraudPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.FraudDecision;
import org.chenile.workflow.api.StateEntityService;

public interface FraudPolicyManager extends StateEntityService<FraudPolicy> {
    FraudDecision evaluateTransaction(String userId, Object transactionData);
}
