package com.homebase.ecom.constants;

/**
 * Constants for cache names and keys used throughout the application.
 */
public final class CacheConstants {

    private CacheConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Cache names
    public static final String CUSTOMER_EXISTS_CACHE = "customerExistsCache";
    public static final String PRODUCT_CACHE = "productCache";
    public static final String CATEGORY_CACHE = "categoryCache";
    public static final String INVENTORY_CACHE = "inventoryCache";

    // Cache key prefixes
    public static final String CUSTOMER_ID_PREFIX = "customer:";
    public static final String PRODUCT_ID_PREFIX = "product:";
}
