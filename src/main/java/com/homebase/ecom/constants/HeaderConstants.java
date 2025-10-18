package com.homebase.ecom.constants;

/**
 * Constants for HTTP headers and Chenile exchange headers.
 */
public final class HeaderConstants {

    private HeaderConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Chenile Exchange Headers
    public static final String HEADER_USER_INFO = "userInfo";
    public static final String HEADER_CUSTOMER_ENTITY = "customerEntity";
    public static final String HEADER_CUSTOMER_ID = "x-customer-id";
    public static final String HEADER_TENANT_ID = "x-tenant-id";
    public static final String HEADER_SUB_TENANT_ID = "x-sub-tenant-id";

    // User Info Map Keys
    public static final String KEY_CUSTOMER_ID = "x-customer-id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
}
