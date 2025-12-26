package com.handmade.ecommerce.policy.domain.valueobject;

/**
 * Policy lifecycle status
 */
public enum PolicyStatus {
    /**
     * Policy is being created, not yet active
     * Can be edited
     */
    DRAFT,
    
    /**
     * Policy is currently in use for new sellers
     * Immutable (cannot be edited)
     */
    ACTIVE,
    
    /**
     * Policy is no longer used for new sellers
     * Existing sellers are grandfathered
     */
    DEPRECATED
}
