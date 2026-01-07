package com.handmade.ecommerce.policy.domain.valueobject;

public enum FraudDecision {
    TRUSTED,
    FLAGGED,
    BLOCKED,
    CHALLENGE_REQUIRED
}
