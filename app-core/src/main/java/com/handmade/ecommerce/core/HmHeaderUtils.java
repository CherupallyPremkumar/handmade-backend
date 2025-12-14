package com.handmade.ecommerce.core;

import org.chenile.core.context.HeaderUtils;

import java.util.Map;

public class HmHeaderUtils extends HeaderUtils {

    public static final String SELLER_ID_KEY = "x-chenile-seller-id";

    public static void setSellerId(Map<String, Object> headers, String sellerId) {
        headers.put(SELLER_ID_KEY,sellerId);
    }

    private static String convertToString(Object o) {
        return (o == null) ? null: o.toString();
    }
    public static String getSellerId(Map<String, Object> headers) {
        return convertToString(headers.get(USER_ID_KEY));
    }
}
