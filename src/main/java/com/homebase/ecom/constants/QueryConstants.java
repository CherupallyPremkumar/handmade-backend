package com.homebase.ecom.constants;

/**
 * Constants for database query names and filters.
 */
public final class QueryConstants {

    private QueryConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Query names
    public static final String QUERY_EXISTS_CUSTOMER = "existsCustomer";
    public static final String QUERY_GET_CUSTOMER_BY_ID = "getCustomerById";
    public static final String QUERY_GET_ALL_CUSTOMERS = "getAll";

    // Filter keys
    public static final String FILTER_CUSTOMER_ID = "customer_id";
    public static final String FILTER_EMAIL = "email";
    public static final String FILTER_TENANT_ID = "tenant_id";
}
