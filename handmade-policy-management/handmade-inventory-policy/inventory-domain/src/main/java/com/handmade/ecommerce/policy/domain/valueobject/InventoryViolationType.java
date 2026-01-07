package com.handmade.ecommerce.policy.domain.valueobject;

public enum InventoryViolationType {
    OUT_OF_STOCK_MISMATCH,
    RESERVED_STOCK_VIOLATION,
    MIN_STOCK_THRESHOLD_BREACH,
    MAX_STOCK_THRESHOLD_BREACH,
    INVALID_STOCK_UPDATE
}
