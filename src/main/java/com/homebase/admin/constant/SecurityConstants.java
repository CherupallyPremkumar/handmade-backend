package com.homebase.admin.constant;

public final class SecurityConstants {

    private SecurityConstants() {}

    // Header
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    // Error messages
    public static final String ERROR_MISSING_TOKEN = "Missing or invalid Authorization header";
    public static final String ERROR_INVALID_TOKEN = "Invalid or expired token";

    // Public endpoints
    public static final String[] PUBLIC_ENDPOINTS = {
            "/api/tenants/",
            "/api/user/auth/",
            "/api/user/auth/login"
    };
}