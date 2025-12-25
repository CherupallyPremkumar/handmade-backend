package com.handmade.ecommerce.platform.application.port.out;

/**
 * Port for authorization checks.
 * Infrastructure will implement this (Spring Security, custom IAM, etc.)
 */
public interface AuthorizationService {
    /**
     * Check if an operator has permission to perform platform governance operations.
     * @param operatorId The operator attempting the action
     * @param permission The required permission
     * @return true if authorized, false otherwise
     */
    boolean hasPermission(String operatorId, String permission);
    
    /**
     * Check if an operator is a platform administrator.
     * @param operatorId The operator to check
     * @return true if operator is admin, false otherwise
     */
    boolean isPlatformAdmin(String operatorId);
}
