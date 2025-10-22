package com.homebase.ecom.constants;

import org.chenile.core.context.HeaderUtils;

import java.util.Map;

public class HmHeaderUtils extends HeaderUtils {

    public static final String SUB_TENANT_ID_KEY = "x-chenile-sub-tenant-id";

    public static void setSubTenantIdKey(Map<String, String> headers, String subTenantId) {
        headers.put(SUB_TENANT_ID_KEY, subTenantId);
    }

    public static String getSubTenantIdKey(Map<String, String> headers) {
        return headers.get(SUB_TENANT_ID_KEY);
    }

}
