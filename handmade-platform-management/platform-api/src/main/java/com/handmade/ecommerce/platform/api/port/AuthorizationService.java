package com.handmade.ecommerce.platform.api.port;

public interface AuthorizationService {
    boolean hasPermission(String operatorId, String permission);
    boolean isPlatformAdmin(String operatorId);
}
